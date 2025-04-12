import React from 'react';
import { Button, Input, Typography } from 'antd';

const { Title } = Typography;

function App() {
  return (
    <div style={{ padding: 40 }}>
      <Title level={2}>Sklep Internetowy</Title>
      <Input placeholder="Szukaj produktu" style={{ marginBottom: 20, width: 300 }} />
      <br />
      <Button type="primary">Szukaj</Button>
    </div>
  );
}

export default App;
