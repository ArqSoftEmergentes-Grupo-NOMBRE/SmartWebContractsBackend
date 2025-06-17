// block.js  → bloque con PoW interno (dificultad fija = 2 ceros)
import crypto from "crypto";
import Contract from "./contract.js";

export default class Block {
    constructor(index, contractObj, previousHash = "0") {
        this.index        = index;
        this.timestamp    = Date.now();
        this.contractHash = contractObj.hash;
        this.contract     = contractObj;         // se guarda íntegro
        this.previousHash = previousHash;
        this.nonce        = 0;
        this.hash         = this.calc();
    }

    calc() {
        return crypto
            .createHash("sha256")
            .update(
                this.index +
                this.previousHash +
                this.timestamp +
                this.contractHash +
                this.nonce +
                JSON.stringify(this.contract.data)
            )
            .digest("hex");
    }

    mine(difficulty) {
        const target = "0".repeat(difficulty);
        while (!this.hash.startsWith(target)) {
            this.nonce++;
            this.hash = this.calc();
        }
    }

    static fromJSON(o) {
        const c  = Contract.fromJSON(o.contract);
        const b  = new Block(o.index, c, o.previousHash);
        b.timestamp    = o.timestamp;
        b.nonce        = o.nonce;
        b.hash         = o.hash;
        b.contractHash = o.contractHash;
        return b;
    }
}
