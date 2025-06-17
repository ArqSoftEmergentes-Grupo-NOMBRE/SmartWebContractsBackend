// contract.js  → objeto contrato + hash único
import crypto from "crypto";

export default class Contract {
    constructor(owner, data) {
        this.owner      = owner;          // quién lo crea
        this.data       = data;           // JSON arbitrario: hitos, pagos, etc.
        this.timestamp  = Date.now();
        this.hash       = crypto
            .createHash("sha256")
            .update(JSON.stringify({ owner, data, ts: this.timestamp }))
            .digest("hex");
    }

    static fromJSON(o) {
        const c = new Contract(o.owner, o.data);
        c.timestamp = o.timestamp;
        c.hash      = o.hash;
        return c;
    }
}
