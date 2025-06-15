import React, { useState } from 'react';
import { Layout, Button } from 'antd';
import Product from './components/Product/Product';
import Category from './components/Category/Category';
import Color from './components/Color/Color';
import Image from './components/Image/Image';
import Size from './components/Size/Size';

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

    const showImages = () => {
        setSelectedComponent('image');
    };

    const showSizes = () => {
        setSelectedComponent('size');
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
                <Button
                    type="primary"
                    style={{ marginRight: '10px' }}
                    onClick={showImages}
                >
                    Image
                </Button>
                <Button
                    type="primary"
                    style={{ marginRight: '10px' }}
                    onClick={showSizes}
                >
                    Size
                </Button>

                {selectedComponent === 'product' && <Product />}
                {selectedComponent === 'category' && <Category />}
                {selectedComponent === 'color' && <Color />}
                {selectedComponent === 'image' && <Image />}
                {selectedComponent === 'size' && <Size />}
            </Content>
        </Layout>
    );
};

export default App;
