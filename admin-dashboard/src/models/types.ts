export interface CallLogEntry {
  id: string;
  callerNumber: string;
  durationSeconds: number;
  intent: string;
  status: string;
}
