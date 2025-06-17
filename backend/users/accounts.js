import bcrypt   from "bcrypt";
import jwt      from "jsonwebtoken";
import {
    loadUsers,
    saveUsers
} from "../storage/storage.js";

const JWT_SECRET = process.env.JWT_SECRET || "tarket-super-secret";
const SALT_ROUNDS = 10;

/* ------------------------------------------------------------------
   Helpers
-------------------------------------------------------------------*/
function findByEmail(email) {
    return loadUsers().find(u => u.email === email.toLowerCase());
}

/* ------------------------------------------------------------------
   Registro de usuario
   - data  => { fullName, email, password, role }
   - throw => Error si el correo ya existe
-------------------------------------------------------------------*/
export function registerUser(data) {
    const users = loadUsers();

    if (findByEmail(data.email))
        throw new Error("El correo ya está registrado");

    const newUser = {
        ...data,
        id       : Date.now().toString(36),              // id simple
        password : bcrypt.hashSync(data.password, SALT_ROUNDS),
        created  : new Date().toISOString()
    };

    users.push(newUser);
    saveUsers(users);

    // genera token inmediato
    const token = jwt.sign({ id: newUser.id, role: newUser.role }, JWT_SECRET, {
        expiresIn: "7d"
    });

    return { token, user: { ...newUser, password: undefined } };
}

/* ------------------------------------------------------------------
   Login
   - creds => { email, password }
   - throw => Error si las credenciales no coinciden
-------------------------------------------------------------------*/
export function loginUser(creds) {
    const user = findByEmail(creds.email);
    if (!user) throw new Error("Credenciales inválidas");

    const ok = bcrypt.compareSync(creds.password, user.password);
    if (!ok) throw new Error("Credenciales inválidas");

    const token = jwt.sign({ id: user.id, role: user.role }, JWT_SECRET, {
        expiresIn: "7d"
    });

    return { token, user: { ...user, password: undefined } };
}

/* ------------------------------------------------------------------
   Middleware Express para autenticar vía JWT
-------------------------------------------------------------------*/
export function authMiddleware(req, res, next) {
    const header = req.headers.authorization || "";
    const [, token] = header.split(" ");

    if (!token) return res.status(401).json({ error: "Token requerido" });

    try {
        req.user = jwt.verify(token, JWT_SECRET);
        next();
    } catch (e) {
        res.status(401).json({ error: "Token inválido" });
    }
}
