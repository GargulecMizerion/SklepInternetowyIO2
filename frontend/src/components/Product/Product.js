import React, { useState, useEffect } from 'react';
import { Layout, Modal, message, Input, Spin, Alert, Popconfirm } from 'antd';
import ProductList from './ProductList';
import AddProductForm from './AddProductForm';
import {
    AddButton,
    Container,
    SearchButton,
    DetailsCard,
    DetailItem,
    DeleteButton
} from '../shared/styles';
import { getAllProducts, getProductById, getProductByCategoryId, deleteProductById } from './ProductService';

const { Content } = Layout;

const Product = () => {
    const [products, setProducts] = useState([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [productId, setProductId] = useState('');
    const [categoryId, setCategoryId] = useState('');
    const [searchedProduct, setSearchedProduct] = useState(null);
    const [loading, setLoading] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [hasSearched, setHasSearched] = useState(false);

    const deleteProduct = async (id) => {
        try {
            await deleteProductById(id);
            getAllProducts().then(setProducts);
            if (searchedProduct) {
                setSearchedProduct(prev => prev.filter(p => p.id !== id));
            }
            setSuccessMessage('Product deleted successfully');
            message.success('Product deleted successfully');
        } catch (error) {
            message.error('Failed to delete product');
        }
    };

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const data = await getAllProducts();
                setProducts(data);
            } catch (error) {
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
            setSearchedProduct([data]);
        } catch (error) {
            setSearchedProduct(null); 
            message.error('Product not found');
        } finally {
            setLoading(false);
        }
    };

    const handleCategorySearch = async () => {
        if (!categoryId) {
            message.error('Please enter a category ID');
            return;
        }

        setLoading(true);
        setHasSearched(true);
        try {
            const data = await getProductByCategoryId(categoryId);
            setSearchedProduct(data);
        } catch (error) {
            setSearchedProduct(null);
            message.error('Product not found for this category ID');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container>
            <Content style={{ padding: '20px' }}>
                <AddButton type="primary" onClick={showModal}>
                    Add Product
                </AddButton>

                <div style={{ marginBottom: '20px' }}>
                    <div style={{ marginBottom: '10px' }}>
                        <Input
                            placeholder="Enter Product ID"
                            value={productId}
                            onChange={(e) => setProductId(e.target.value)}
                            style={{ width: '200px', marginBottom: '10px' }}
                        />
                        <SearchButton
                            type="primary"
                            onClick={handleSearch}
                            loading={loading}
                        >
                            Search by Product ID
                        </SearchButton>
                    </div>
                    <div>
                        <Input
                            placeholder="Enter Category ID"
                            value={categoryId}
                            onChange={(e) => setCategoryId(e.target.value)}
                            style={{ width: '200px', marginBottom: '10px' }}
                        />
                        <SearchButton
                            type="primary"
                            onClick={handleCategorySearch}
                            loading={loading}
                        >
                            Search by Category ID
                        </SearchButton>
                    </div>
                </div>

                {loading ? (
                    <Spin size="large" style={{ display: 'block', marginTop: '20px' }} />
                ) : hasSearched && Array.isArray(searchedProduct) && searchedProduct.length > 0 ? (
                    <>
                        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '30px', margin: '30px 0' }}>
                            {searchedProduct.map((img) => (
                                <DetailsCard key={img.id} style={{ margin: '0px', position: 'relative' }}>
                                    <h3 style={{ color: '#f326be', marginBottom: '16px' }}>Product Details</h3>
                                    <DetailItem><span>ID:</span> {img.id}</DetailItem>
                                    <DetailItem><span>Name:</span> {img.name}</DetailItem>
                                    <DetailItem><span>Description:</span> {img.description}</DetailItem>
                                    <DetailItem>
                                        <span>Category:</span> {img.category ? img.category.name : 'No category'}
                                    </DetailItem>
                                    <Popconfirm
                                        title="Are you sure you want to delete this product?"
                                        onConfirm={() => deleteProduct(img.id)}
                                        okText="Yes"
                                        cancelText="No"
                                    >
                                        <DeleteButton style={{ position: 'absolute', top: 31, right: 21 }}>
                                            Delete
                                        </DeleteButton>
                                    </Popconfirm>
                                </DetailsCard>
                            ))}
                        </div>
                    </>
                ) : hasSearched ? (
                    <Alert
                        message="Product(s) not found"
                        type="warning"
                        showIcon
                        style={{ margin: '20px 0' }}
                    />
                ) : null}

                <ProductList
                    products={products}
                    onDelete={deleteProduct}
                />
                
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
        </Container>
    );
};

export default Product;
