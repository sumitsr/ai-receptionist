import React from 'react';
import { useFetchResult } from '../hooks/useFetchResult';
import type { CallLogEntry } from '../models/types';

export const CallLog: React.FC = () => {
  const { data, error, loading } = useFetchResult<CallLogEntry[]>('/api/logs');

  if (loading) return <div>Loading timeline...</div>;
  if (error) return <div>Error retrieving logs: {error}</div>;
  if (!data) return <div>No logs available.</div>;

  return (
    <div className="log-container">
      <h2>Call Execution Ledger</h2>
      {data.map(renderLogEntry)}
    </div>
  );
};

const renderLogEntry = (log: CallLogEntry) => (
  <div key={log.id} className="log-row">
    <span>{log.callerNumber}</span> | <span>{log.intent}</span> | <span>{log.status}</span>
  </div>
);
