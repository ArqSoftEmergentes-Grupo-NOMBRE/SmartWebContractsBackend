import fs from "fs";
const DATA_DIR = "./backend/storage/data";
const CHAIN    = `${DATA_DIR}/chain.json`;
const QUEUE    = `${DATA_DIR}/queue.json`;
const DIFF     = `${DATA_DIR}/difficulty.json`;
const USERS    = `${DATA_DIR}/users.json`;
const SUBMISSIONS= `${DATA_DIR}/submissions.json`;

if (!fs.existsSync(DATA_DIR)) fs.mkdirSync(DATA_DIR);

export function save(file, data) {
    fs.writeFileSync(file, JSON.stringify(data, null, 2));
}

export function load(file, fallback) {
    try {
        return fs.existsSync(file)
            ? JSON.parse(fs.readFileSync(file))
            : fallback;
    } catch (e) {
        return fallback;
    }
}

export const loadChain = () => load(CHAIN, []);
export const saveChain = (c) => save(CHAIN, c);

export const loadQueue = () => load(QUEUE, []);
export const saveQueue = (q) => save(QUEUE, q);

export const loadDifficulty = () => load(DIFF, 6);
export const saveDifficulty = (d) => save(DIFF, d);

export const loadUsers = () => load(USERS, []);
export const saveUsers = (u) => save(USERS, u);

export const loadSubmissions = () => load(SUBMISSIONS, []);
export const saveSubmissions = (m) => save(SUBMISSIONS, m);