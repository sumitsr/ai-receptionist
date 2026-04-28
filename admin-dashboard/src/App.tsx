import React from 'react';
import { CallLog } from './pages/CallLog';

export const App: React.FC = () => {
  return (
    <main className="dashboard-layout">
      <header><h1>AI Receptionist Command Center</h1></header>
      <CallLog />
    </main>
  );
};

export default App;
