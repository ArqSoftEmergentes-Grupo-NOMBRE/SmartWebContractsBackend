import { parentPort } from "worker_threads";
import crypto from "crypto";

parentPort.on("message", ({ block, difficulty }) => {
    const target = "0".repeat(difficulty);
    const data = JSON.stringify(block.contract.data);
    while (!block.hash.startsWith(target)) {
        block.nonce++;
        block.hash = crypto
            .createHash("sha256")
            .update(
                block.index +
                block.previousHash +
                block.timestamp +
                block.contractHash +
                block.nonce +
                data
            ).digest("hex");
    }
    parentPort.postMessage(block);   // devuélvelo listo
});