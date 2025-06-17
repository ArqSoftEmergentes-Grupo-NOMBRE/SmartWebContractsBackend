import { Worker } from "worker_threads";
import path from "path";
import Block from "./block.js";
import Contract from "./contract.js";
import { loadChain, saveChain, loadQueue, saveQueue, loadDifficulty, saveDifficulty } from "../storage/storage.js";

// Utilidad: ejecuta worker y devuelve Promesa
function mineInWorker(block, difficulty) {
    return new Promise((resolve, reject) => {
        const workerPath = path.resolve("backend/blockchain/miner.js");
        const w = new Worker(workerPath, { type: "module" });

        w.postMessage({ block, difficulty });
        w.on("message", minedBlock => {
            w.terminate();
            resolve(minedBlock);
        });
        w.on("error", err => {
            w.terminate();
            reject(err);
        });
    });
}

export default class Blockchain {
    constructor() {
        this.chain = loadChain();

        // Convierte los contratos a objetos Contract
        this.chain = this.chain.map(b => {
            const contract = Contract.fromJSON(b.contract);
            return Block.fromJSON({ ...b, contract });
        });

        this.queue  = loadQueue();
        this.mining = false;
        this.difficulty = loadDifficulty();           // default (0000-…)
        this.maxWorkers = 1;                          // 1 a la vez para tu requisito
    }

    createContract(owner, data) {
        const c = new Contract(owner, data);
        this.queue.push(c);
        saveQueue(this.queue);
        this.#tryMine();
        return c.hash;
    }

    async #tryMine() {
        if (this.mining || this.queue.length === 0) return;
        this.mining = true;

        console.log("⚙️ Minado iniciado. Bloques en cola:", this.queue.length);

        while (this.queue.length > 0) {
            const contractObj = this.queue.shift();
            saveQueue(this.queue);


            let block = new Block(
                this.chain.length,
                contractObj,
                this.chain.length?this.chain[this.chain.length - 1].hash:"0".repeat(64),
            );

            // Minar sin bloquear usando el worker
            block = await mineInWorker(block, this.difficulty);

            this.chain.push(block);
            saveChain(this.chain);

            console.log(
                `✅ Bloque #${block.index} minado (${block.hash.slice(0, 10)}…). ` +
                `En cola: ${this.queue.length}`
            );
        }

        this.mining = false;
        console.log("⛏️ Minado finalizado. Cola vacía.");
    }

    /* --------- utilidades ---------- */
    getContract(hash) {
        return this.chain.find(b => b.contractHash === hash)?.contract || null;
    }

    status() {
        return {
            height : this.chain.length,
            queued : this.queue.length,
            mining : this.mining,
            diff   : this.difficulty
        };
    }

    setDifficulty(n) {
        if (n < 1) throw new Error("La dificultad debe ser ≥ 1");
        if (n === this.difficulty) return; // nada que hacer}
        saveDifficulty(n); // guarda en disco
        console.log(`🔧 Dificultad cambiada a ${n}`);
        this.difficulty = n;
    }

    /** Valida la cadena completa contra la dificultad actual. */
    validate() {
        const targetPrefix = "0".repeat(this.difficulty);

        for (let i = 1; i < this.chain.length; i++) {
            const block = this.chain[i];

            /* 1.  Verifica integridad interna del bloque */
            const recalculatedHash = block.calc();
            if (block.hash !== recalculatedHash)
                return { ok: false, at: i, reason: "Hash almacenado ≠ recalculado" };

            /* 2.  Cumple prueba de trabajo actual */
            if (!block.hash.startsWith(targetPrefix))
                return { ok: false, at: i, reason: `Hash no cumple dificultad ${this.difficulty}` };

            /* 3.  Enlaces correctos con bloque anterior (excepto génesis) */
            if (i > 0 && block.previousHash !== this.chain[i - 1].hash)
                return { ok: false, at: i, reason: "previousHash roto" };
        }

        /* 4.  Opcional: verifica que el hash del contrato coincide */
        for (const b of this.chain) {
            if (b.contractHash !== b.contract.hash)
                return { ok: false, at: b.index, reason: "contractHash ≠ hash del contrato" };
        }

        return { ok: true };
    }

}