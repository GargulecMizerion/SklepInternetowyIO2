import React, { useState, useEffect } from 'react';
import { Layout, Modal, message, Input, Spin, Alert } from 'antd';
import ProductList from './ProductList';
import AddProductForm from './AddProductForm';
import {
    AddProductButton,
    ProductContainer,
    SearchProductButton,
    ProductDetailsCard,
    ProductDetailItem
} from './styles';
import { getAllProducts, getProductById } from './ProductService';

const { Content } = Layout;

const Product = () => {
    const [products, setProducts] = useState([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [productId, setProductId] = useState('');
    const [searchedProduct, setSearchedProduct] = useState(null);
    const [loading, setLoading] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [hasSearched, setHasSearched] = useState(false); 

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const data = await getAllProducts();
                console.log("Fetched products:", data);
                setProducts(data);
            } catch (error) {
                console.error("Fetch error:", error);
                message.error('Failed to fetch products');
            }
        };
        fetchProducts();
    }, []);

    const showModal = () => setIsModalVisible(true);
    const handleOk = () => {
        setIsModalVisible(false);
        setSuccessMessage('Product added successfully');
    };
    const handleCancel = () => setIsModalVisible(false);

    const handleSearch = async () => {
        if (!productId) {
            message.error('Please enter a product ID');
            return;
        }

        setLoading(true);
        setHasSearched(true);
        try {
            const data = await getProductById(productId);
            setSearchedProduct(data);
        } catch (error) {
            setSearchedProduct(null); 
            message.error('Product not found');
        } finally {
            setLoading(false);
        }
    };

    return (
        <ProductContainer>
            <Content style={{ padding: '20px' }}>
                <AddProductButton type="primary" onClick={showModal}>
                    Add Product
                </AddProductButton>

                <div style={{ marginBottom: '20px' }}>
                    <Input
                        placeholder="Enter Product ID"
                        value={productId}
                        onChange={(e) => setProductId(e.target.value)}
                        style={{ width: '200px', marginBottom: '10px' }}
                    />
                    <SearchProductButton
                        type="primary"
                        onClick={handleSearch}
                        loading={loading}
                    >
                        Search Product
                    </SearchProductButton>
                </div>

                {loading ? (
                    <Spin size="large" style={{ display: 'block', marginTop: '20px' }} />
                ) : hasSearched && searchedProduct ? (
                    <ProductDetailsCard>
                        <h3 style={{ color: '#f326be', marginBottom: '16px' }}>Product Details</h3>
                        <ProductDetailItem><span>Name:</span> {searchedProduct.name}</ProductDetailItem>
                        <ProductDetailItem><span>Description:</span> {searchedProduct.description}</ProductDetailItem>
                        <ProductDetailItem>
                            <span>Category:</span> {searchedProduct.category ? searchedProduct.category.name : 'No category'}
                        </ProductDetailItem>
                    </ProductDetailsCard>
                ) : hasSearched && !searchedProduct ? (
                    <Alert
                        message="Product not found"
                        type="warning"
                        showIcon
                        style={{ marginTop: '20px' }}
                    />
                ) : null}

                <ProductList products={products} />
                
                {successMessage && (
                    <Alert
                        message={successMessage}
                        type="success"
                        showIcon
                        closable
                        style={{ marginTop: '16px' }}
                    />
                )}

                <Modal
                    title="Add Product"
                    open={isModalVisible}
                    onOk={handleOk}
                    onCancel={handleCancel}
                    footer={null}
                >
                    <AddProductForm 
                        onSuccess={() => {
                            handleOk(); 
                            getAllProducts().then(setProducts);
                        }}
                    />
                </Modal>
            </Content>
        </ProductContainer>
    );
};

export default Product;
