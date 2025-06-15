import React, { useState, useEffect } from 'react';
import { Layout, Modal, message, Input, Spin, Alert, Popconfirm } from 'antd';
import StockList from './StockList';
import AddStockForm from './AddStockForm';
import {
    AddButton,
    Container,
    SearchButton,
    DetailsCard,
    DetailItem,
    DeleteButton
} from '../shared/styles';
import { getAllStock, getStockById, getStockByProductId, deleteStockById } from './StockService';

const { Content } = Layout;

const Stock = () => {
    const [stock, setStock] = useState([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [stockId, setStockId] = useState('');
    const [productId, setProductId] = useState('');
    const [searchedStock, setSearchedStock] = useState(null);
    const [loading, setLoading] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [hasSearched, setHasSearched] = useState(false);

    const deleteStock = async (id) => {
        try {
            await deleteStockById(id);
            getAllStock().then(setStock);
            if (searchedStock) {
                setSearchedStock(prev => prev.filter(p => p.id !== id));
            }
            setSuccessMessage('Stock deleted successfully');
            message.success('Stock deleted successfully');
        } catch (error) {
            message.error('Failed to delete stock');
        }
    };

    useEffect(() => {
        const fetchStock = async () => {
            try {
                const data = await getAllStock();
                setStock(data);
            } catch (error) {
                message.error('Failed to fetch stock');
            }
        };
        fetchStock();
    }, []);

    const showModal = () => setIsModalVisible(true);
    const handleOk = () => {
        setIsModalVisible(false);
        setSuccessMessage('Stock added successfully');
    };
    const handleCancel = () => setIsModalVisible(false);

    const handleSearch = async () => {
        if (!stockId) {
            message.error('Please enter a stock ID');
            return;
        }

        setLoading(true);
        setHasSearched(true);
        try {
            const data = await getStockById(stockId);
            setSearchedStock([data]);
        } catch (error) {
            setSearchedStock(null);
            message.error('Stock not found');
        } finally {
            setLoading(false);
        }
    };

    const handleProductSearch = async () => {
        if (!productId) {
            message.error('Please enter a product ID');
            return;
        }

        setLoading(true);
        setHasSearched(true);
        try {
            const data = await getStockByProductId(productId);
            setSearchedStock(data);
        } catch (error) {
            setSearchedStock(null);
            message.error('Stock not found for this product ID');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container>
            <Content style={{ padding: '20px' }}>
                <AddButton type="primary" onClick={showModal}>
                    Add Stock
                </AddButton>

                <div style={{ marginBottom: '20px' }}>
                    <div style={{ marginBottom: '10px' }}>
                        <Input
                            placeholder="Enter Stock ID"
                            value={stockId}
                            onChange={(e) => setStockId(e.target.value)}
                            style={{ width: '200px', marginBottom: '10px' }}
                        />
                        <SearchButton
                            type="primary"
                            onClick={handleSearch}
                            loading={loading}
                        >
                            Search by Stock ID
                        </SearchButton>
                    </div>
                    <div>
                        <Input
                            placeholder="Enter Product ID"
                            value={productId}
                            onChange={(e) => setProductId(e.target.value)}
                            style={{ width: '200px', marginBottom: '10px' }}
                        />
                        <SearchButton
                            type="primary"
                            onClick={handleProductSearch}
                            loading={loading}
                        >
                            Search by Product ID
                        </SearchButton>
                    </div>
                </div>

                {loading ? (
                    <Spin size="large" style={{ display: 'block', marginTop: '20px' }} />
                ) : hasSearched && Array.isArray(searchedStock) && searchedStock.length > 0 ? (
                    <>
                        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '30px', margin: '30px 0' }}>
                            {searchedStock.map((img) => (
                                <DetailsCard key={img.id} style={{ margin: '0px', position: 'relative' }}>
                                    <h3 style={{ color: '#f326be', marginBottom: '16px' }}>Stock Details</h3>
                                    <DetailItem><span>ID:</span> {img.id}</DetailItem>
                                    <DetailItem>
                                        <span>Product:</span> {img.product ? img.product.name : 'No product'}
                                    </DetailItem>
                                    <DetailItem>
                                        <span>Size:</span> {img.size ? img.size.sizeValue : 'No size'}
                                    </DetailItem>
                                    <DetailItem>
                                        <span>Color:</span> {img.color ? img.color.color : 'No color'}
                                    </DetailItem>
                                    <DetailItem><span>Quantity:</span> {img.quantity}</DetailItem>
                                    <DetailItem><span>Price:</span> {img.price}</DetailItem>
                                    <Popconfirm
                                        title="Are you sure you want to delete this stock?"
                                        onConfirm={() => deleteStock(img.id)}
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
                        message="Stock not found"
                        type="warning"
                        showIcon
                        style={{ margin: '20px 0' }}
                    />
                ) : null}

                <StockList
                    stock={stock}
                    onDelete={deleteStock}
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
                    title="Add Stock"
                    open={isModalVisible}
                    onOk={handleOk}
                    onCancel={handleCancel}
                    footer={null}
                >
                    <AddStockForm
                        onSuccess={() => {
                            handleOk();
                            getAllStock().then(setStock);
                        }}
                    />
                </Modal>
            </Content>
        </Container>
    );
};

export default Stock;
