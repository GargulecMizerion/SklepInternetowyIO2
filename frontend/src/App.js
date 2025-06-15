import React, { useState } from 'react';
import { Layout, Button } from 'antd';
import Product from './components/Product/Product'; 
import Category from './components/Category/Category';  
import Product from './components/Product/Product';
import Category from './components/Category/Category';
import Color from './components/Color/Color';

const { Content } = Layout;

const App = () => {
    const [selectedComponent, setSelectedComponent] = useState(null);

    const showProducts = () => {
        setSelectedComponent('product');
    };

    const showCategories = () => {
        setSelectedComponent('category');
    };

    const showColors = () => {
        setSelectedComponent('color');
    };

    return (
        <Layout style={{ minHeight: '100vh' }}>
            <Content style={{ padding: '50px' }}>
                <Button
                    type="primary"
                    style={{ marginRight: '10px' }}
                    onClick={showProducts}
                >
                    Product
                </Button>
                <Button
                    type="primary"
                    style={{ marginRight: '10px' }}
                    onClick={showCategories}
                >
                    Category
                </Button>
                <Button
                    type="primary"
                    style={{ marginRight: '10px' }}
                    onClick={showColors}
                >
                    Color
                </Button>

                {selectedComponent === 'product' && <Product />}
                {selectedComponent === 'category' && <Category />}
                {selectedComponent === 'color' && <Color />}
            </Content>
        </Layout>
    );
};

export default App;
