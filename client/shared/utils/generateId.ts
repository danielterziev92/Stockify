export function generateId(): string {
    return Math.floor(Math.random() * 1_000_000).toString();
}