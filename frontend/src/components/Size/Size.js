import React, { useState, useEffect } from 'react';
import { Layout, Modal, message, Input, Spin, Alert, Popconfirm } from 'antd';
import SizeList from './SizeList';
import AddSizeForm from './AddSizeForm';
import {
    AddButton,
    Container,
    SearchButton,
    DetailsCard,
    DetailItem,
    DeleteButton
} from '../shared/styles';
import { getAllSizes, getSizeById, deleteSizeById } from './SizeService';

const { Content } = Layout;

const Size = () => {
    const [sizes, setSizes] = useState([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [sizeId, setSizeId] = useState('');
    const [searchedSize, setSearchedSize] = useState(null);
    const [loading, setLoading] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [hasSearched, setHasSearched] = useState(false);

    const deleteSize = async (id) => {
            try {
                await deleteSizeById(id);
                getAllSizes().then(setSizes);
                if (searchedSize) {
                    setSearchedSize(null);
                }
                setSuccessMessage('Size deleted successfully');
                message.success('Size deleted successfully');
            } catch (error) {
                message.error('Failed to delete size');
            }
        };

    useEffect(() => {
        const fetchSizes = async () => {
            try {
                const data = await getAllSizes();
                setSizes(data);
            } catch (error) {
                message.error('Failed to fetch sizes');
            }
        };
        fetchSizes();
    }, []);

    const showModal = () => setIsModalVisible(true);
    const handleOk = () => {
        setIsModalVisible(false);
        setSuccessMessage('Size added successfully');
    };
    const handleCancel = () => setIsModalVisible(false);

    const handleSearch = async () => {
        if (!sizeId) {
            message.error('Please enter a size ID');
            return;
        }

        setLoading(true);
        setHasSearched(true);
        try {
            const data = await getSizeById(sizeId);
            setSearchedSize(data);
        } catch (error) {
            setSearchedSize(null);
            message.error('Size not found');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container>
            <Content style={{ padding: '20px' }}>
                <AddButton type="primary" onClick={showModal}>
                    Add Size
                </AddButton>

                <div style={{ marginBottom: '20px' }}>
                    <Input
                        placeholder="Enter Size ID"
                        value={sizeId}
                        onChange={(e) => setSizeId(e.target.value)}
                        style={{ width: '200px', marginBottom: '10px' }}
                    />
                    <SearchButton
                        type="primary"
                        onClick={handleSearch}
                        loading={loading}
                    >
                        Search Size
                    </SearchButton>
                </div>

                {loading ? (
                    <Spin size="large" style={{ display: 'block', marginTop: '20px' }} />
                ) : hasSearched && searchedSize ? (
                    <DetailsCard style={{ position: 'relative' }}>
                        <h3 style={{ size: '#f326be', marginBottom: '16px' }}>Size Details</h3>
                        <DetailItem><span>ID:</span> {searchedSize.id}</DetailItem>
                        <DetailItem><span>Size:</span> {searchedSize.sizeValue}</DetailItem>
                        <DetailItem>
                            <span>Category:</span> {searchedSize.category ? searchedSize.category.name : 'No category'}
                        </DetailItem>
                        <Popconfirm
                            title="Are you sure you want to delete this size?"
                            onConfirm={() => deleteSize(searchedSize.id)}
                            okText="Yes"
                            cancelText="No"
                        >
                            <DeleteButton style={{ position: 'absolute', top: 31, right: 21 }}>
                                Delete
                            </DeleteButton>
                        </Popconfirm>
                    </DetailsCard>
                ) : hasSearched && !searchedSize ? (
                    <Alert
                        message="Size not found"
                        type="warning"
                        showIcon
                        style={{ margin: '20px 0' }}
                    />
                ) : null}

                <SizeList
                    sizes={sizes}
                    onDelete={deleteSize}
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
                    title="Add Size"
                    open={isModalVisible}
                    onOk={handleOk}
                    onCancel={handleCancel}
                    footer={null}
                >
                    <AddSizeForm
                        onSuccess={() => {
                            handleOk();
                            getAllSizes().then(setSizes);
                        }}
                    />
                </Modal>
            </Content>
        </Container>
    );
};

export default Size;
