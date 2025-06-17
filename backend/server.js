// server.js ─ API REST + Swagger UI
import express       from "express";
import swaggerUi     from "swagger-ui-express";
import swaggerJsdoc  from "swagger-jsdoc";
import path          from "path";
import fs            from "fs";
import https         from "https";

import { fileURLToPath } from "url";
import Blockchain    from "./blockchain/blockchain.js";
import cors              from "cors";
import { registerUser, loginUser } from "./users/accounts.js";
import { getAllMilestones, getMilestonesByIndex,addMilestone} from "./milestones/milestones.js";

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const app  = express();
const bc   = new Blockchain();
const PORT = 4000;

app.use(
    cors({
        origin: "*",                // cámbialo a tu dominio/front si quieres restringir
        methods: ["GET", "POST"],
    })
);

app.use(express.json());

/* ------------------------------------------------------------------
   1) SWAGGER CONFIG
-------------------------------------------------------------------*/
const swaggerSpec = swaggerJsdoc({
    definition: {
        openapi: "3.0.0",
        info   : { title: "TARKET Contracts API", version: "1.0.0" },
        servers: [{ url: "https://contracts.backend.tarket.site:3029", description: "Local dev" }],
        tags   : [
            { name: "Blockchain", description: "Operaciones de la cadena" },
            { name: "Contracts", description: "Operaciones de contratos" },
            { name: "Milestones", description: "Operaciones de hitos/entregas" }
        ]
    },
    apis: [path.join(__dirname, "**/*.js")]
});

app.get("/", (_, res) => res.redirect("/api-docs"));
app.use("/api-docs", swaggerUi.serve, swaggerUi.setup(swaggerSpec));

/* ------------------------------------------------------------------
   2) ENDPOINTS
-------------------------------------------------------------------*/

/* ------------- CONTRATOS (sin tag) ------------------------------- */
/**
 * @swagger
 * /contracts:
 *   post:
 *     tags: [Contracts]
 *     summary: Crea un contrato JSON y lo agrega a la cola/minado.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               owner:
 *                 type: string
 *                 example: "Javier Torres"
 *               data:
 *                 type: object
 *                 example: { "titulo": "Landing", "monto": 350 }
 *     responses:
 *       201:
 *         description: Contrato en cola o minado.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 hash:    { type: string }
 *                 message: { type: string }
 */
app.post("/contracts", (req, res) => {
    const { owner = "anon", data = {} } = req.body;
    try {
        const hash = bc.createContract(owner, data);
        res.status(201).json({ hash, message: "Contrato en cola/minado" });
    } catch (e) {
        res.status(400).json({ error: e.message });
    }
});

/**
 * @swagger
 * /contracts/{hash}:
 *   get:
 *     summary: Obtiene el contrato por su hash.
 *     tags: [Contracts]
 *     parameters:
 *       - in: path
 *         name: hash
 *         schema: { type: string }
 *         required: true
 *     responses:
 *       200: { description: JSON del contrato }
 *       404: { description: Contrato no encontrado }
 */
app.get("/contracts/:hash", (req, res) => {
    const c = bc.getContract(req.params.hash);
    if (!c) return res.status(404).json({ error: "Contrato no encontrado" });
    res.json(c);
});

/* ------------- BLOQUE CADENA (tag: Blockchain) ------------------- */
/**
 * @swagger
 * /chain:
 *   get:
 *     tags: [Blockchain]
 *     summary: Devuelve la cadena completa.
 *     responses:
 *       200:
 *         description: Lista de bloques
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items: { type: object }
 */
app.get("/chain", (_, res) => res.json(bc.chain));

/**
 * @swagger
 * /queue:
 *   get:
 *     tags: [Blockchain]
 *     summary: Cantidad de contratos pendientes en cola.
 *     responses:
 *       200:
 *         description: Número en cola
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 queued: { type: integer }
 */
app.get("/queue", (_, res) => res.json({ queued: bc.queue.length }));

/**
 * @swagger
 * /status:
 *   get:
 *     tags: [Blockchain]
 *     summary: Estado general de la plataforma.
 *     responses:
 *       200:
 *         description: Detalle de altura, cola y minado
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 height : { type: integer }
 *                 queued : { type: integer }
 *                 mining : { type: boolean }
 *                 diff   : { type: integer }
 */
app.get("/status", (_, res) => res.json(bc.status()));

/**
 * @swagger
 * /difficulty:
 *   get:
 *     tags: [Blockchain]
 *     summary: Dificultad actual del PoW.
 *     responses:
 *       200:
 *         description: Dificultad actual
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 difficulty: { type: integer }
 */
app.get("/difficulty", (_, res) => res.json({ difficulty: bc.difficulty }));

/**
 * @swagger
 * /difficulty:
 *   post:
 *     tags: [Blockchain]
 *     summary: Actualiza la dificultad del PoW.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               difficulty:
 *                 type: integer
 *                 example: 2
 *                 description: "Nueva dificultad (>= 1)"
 *     responses:
 *       200:
 *         description: Dificultad actualizada
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 difficulty: { type: integer }
 */
app.post("/difficulty", (req, res) => {
    try {
        bc.setDifficulty(req.body.difficulty);
        res.json({ difficulty: bc.difficulty });
    } catch (e) {
        res.status(400).json({ error: e.message });
    }
});

/**
 * @swagger
 * /validate:
 *   get:
 *     tags: [Blockchain]
 *     summary: Valida la cadena completa.
 *     responses:
 *       200:
 *         description: Cadena válida
 *       400:
 *         description: Cadena inválida
 */
app.get("/validate", (_, res) => {
    const r = bc.validate();
    if (r.ok) return res.json({ ok: true });
    res.status(400).json(r);
});

/* ------------------------------------------------------------------*/


app.post("/auth/register", (req, res) => {
    try { res.json(registerUser(req.body)); }
    catch (e) { res.status(400).json({ error: e.message }); }
});

app.post("/auth/login", (req, res) => {
    try { res.json(loginUser(req.body)); }
    catch (e) { res.status(400).json({ error: e.message }); }
});

/* ------------- HITOS / ENTREGAS (tag: Milestones) ----------------- */

/**
 * @swagger
 * /milestones:
 *   get:
 *     tags: [Milestones]
 *     summary: Lista todos los hitos de todos los contratos.
 *     responses:
 *       200:
 *         description: Arreglo con todos los hitos
 */
app.get("/milestones", (_, res) => {
    res.json(getAllMilestones());
});

/**
 * @swagger
 * /milestones/{index}:
 *   get:
 *     tags: [Milestones]
 *     summary: Lista los hitos de un contrato concreto.
 *     parameters:
 *       - in: path
 *         name: index
 *         schema: { type: integer }
 *         required: true
 *     responses:
 *       200: { description: Lista de hitos }
 */
app.get("/milestones/:index", (req, res) => {
    res.json(getMilestonesByIndex(req.params.index));
});

/**
 * @swagger
 * /milestones/{index}:
 *   post:
 *     tags: [Milestones]
 *     summary: Añade un hito a un contrato.
 *     parameters:
 *       - in: path
 *         name: index
 *         schema: { type: integer }
 *         required: true
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               milestone:  { type: integer, example: 0 }
 *               submission:
 *                 type: object
 *                 properties:
 *                   url:      { type: string,  example: "https://example.com" }
 *                   status:   { type: string,  example: "Pending" }
 *                   comments: { type: string,  example: "First draft" }
 *     responses:
 *       201: { description: Hito añadido }
 */
app.post("/milestones/:index", (req, res) => {
    const { milestone, submission } = req.body;
    if (milestone === undefined || !submission)
        return res.status(400).json({ error: "Datos incompletos" });

    const updated = addMilestone(req.params.index, milestone, submission);
    res.status(201).json(updated);
});


/* ------------------------------------------------------------------*/
try {
    /* rutas típicas de certbot / LetsEncrypt —
       ajusta si tus certificados están en otra carpeta/nombre */
    const certDir = path.join(
        "C:", "Certbot", "live", "contracts.backend.tarket.site"
    );
    const options = {
        cert: fs.readFileSync(`${certDir}/fullchain.pem`, "utf8"),
        key : fs.readFileSync(`${certDir}/privkey.pem`,  "utf8"),
    };
    https.createServer(options, app).listen(PORT, () => {
        console.log(`🔒  API  → https://contracts.backend.tarket.site (port ${PORT})`);
        console.log(`🔒  Swagger → https://contracts.backend.tarket.site/api-docs`);
    });

} catch (err) {
    // fallback a HTTP si los certs no están disponibles
    app.listen(PORT, () => {
        console.log(`API     → http://localhost:${PORT}`);
        console.log(`Swagger → http://localhost:${PORT}/api-docs`);
    });
}