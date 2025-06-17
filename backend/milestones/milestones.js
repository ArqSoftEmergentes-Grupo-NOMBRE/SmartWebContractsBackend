import {
    loadSubmissions,
    saveSubmissions
} from "../storage/storage.js";

/* ------------------------------------------------------------------
   1)  Listar TODAS las entregas de todos los contratos
   -----------------------------------------------------------------*/

const submissionsCache = loadSubmissions();
export function getAllMilestones() {
    return submissionsCache
}

/* ------------------------------------------------------------------
   2)  Listar las entregas de un contrato por su índice
       - contractIndex (number | string)
       - return [] si no existe
   -----------------------------------------------------------------*/
export function getMilestonesByIndex(contractIndex) {
    const found = submissionsCache.find((c) => c.index === Number(contractIndex));
    return found ? found.submissions : [];
}

/* ------------------------------------------------------------------
   3)  Agregar un milestone a un contrato
       - contractIndex (number | string)
       - milestoneNumber (number)
       - submissionObj  (object)  → { url, status, comments, ... }
   -----------------------------------------------------------------*/
export function addMilestone(contractIndex, milestoneNumber, submissionObj) {
    const all = loadSubmissions();
    const idx = Number(contractIndex);

    // busca contrato
    let contract = all.find((c) => c.index === idx);

    // si no existe, crea contenedor
    if (!contract) {
        contract = { index: idx, submissions: [] };
        all.push(contract);
    }

    // push nueva entrega
    contract.submissions.push({
        milestone: Number(milestoneNumber),
        submission: submissionObj,
    });

    // persiste
    saveSubmissions(all);

    return contract.submissions;
}
