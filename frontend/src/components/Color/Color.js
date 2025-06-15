import React, { useState, useEffect } from 'react';
import { Layout, Modal, message, Input, Spin, Alert, Popconfirm } from 'antd';
import ColorList from './ColorList';
import AddColorForm from './AddColorForm';
import {
    AddButton,
    Container,
    SearchButton,
    DetailsCard,
    DetailItem,
    DeleteButton
} from '../shared/styles';
import { getAllColors, getColorById, deleteColorById } from './ColorService';

const { Content } = Layout;

const Color = () => {
    const [colors, setColors] = useState([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [colorId, setColorId] = useState('');
    const [searchedColor, setSearchedColor] = useState(null);
    const [loading, setLoading] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [hasSearched, setHasSearched] = useState(false);

    const deleteColor = async (id) => {
            try {
                await deleteColorById(id);
                getAllColors().then(setColors);
                if (searchedColor) {
                    setSearchedColor(null);
                }
                setSuccessMessage('Color deleted successfully');
                message.success('Color deleted successfully');
            } catch (error) {
                message.error('Failed to delete color');
            }
        };

    useEffect(() => {
        const fetchColors = async () => {
            try {
                const data = await getAllColors();
                setColors(data);
            } catch (error) {
                message.error('Failed to fetch colors');
            }
        };
        fetchColors();
    }, []);

    const showModal = () => setIsModalVisible(true);
    const handleOk = () => {
        setIsModalVisible(false);
        setSuccessMessage('Color added successfully');
    };
    const handleCancel = () => setIsModalVisible(false);

    const handleSearch = async () => {
        if (!colorId) {
            message.error('Please enter a color ID');
            return;
        }

        setLoading(true);
        setHasSearched(true);
        try {
            const data = await getColorById(colorId);
            setSearchedColor(data);
        } catch (error) {
            setSearchedColor(null);
            message.error('Color not found');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container>
            <Content style={{ padding: '20px' }}>
                <AddButton type="primary" onClick={showModal}>
                    Add Color
                </AddButton>

                <div style={{ marginBottom: '20px' }}>
                    <Input
                        placeholder="Enter Color ID"
                        value={colorId}
                        onChange={(e) => setColorId(e.target.value)}
                        style={{ width: '200px', marginBottom: '10px' }}
                    />
                    <SearchButton
                        type="primary"
                        onClick={handleSearch}
                        loading={loading}
                    >
                        Search Color
                    </SearchButton>
                </div>

                {loading ? (
                    <Spin size="large" style={{ display: 'block', marginTop: '20px' }} />
                ) : hasSearched && searchedColor ? (
                    <DetailsCard style={{ position: 'relative' }}>
                        <h3 style={{ color: '#f326be', marginBottom: '16px' }}>Color Details</h3>
                        <DetailItem><span>ID:</span> {searchedColor.id}</DetailItem>
                        <DetailItem><span>Color:</span> {searchedColor.color}</DetailItem>
                        <Popconfirm
                            title="Are you sure you want to delete this color?"
                            onConfirm={() => deleteColor(searchedColor.id)}
                            okText="Yes"
                            cancelText="No"
                        >
                            <DeleteButton style={{ position: 'absolute', top: 31, right: 21 }}>
                                Delete
                            </DeleteButton>
                        </Popconfirm>
                    </DetailsCard>
                ) : hasSearched && !searchedColor ? (
                    <Alert
                        message="Color not found"
                        type="warning"
                        showIcon
                        style={{ margin: '20px 0' }}
                    />
                ) : null}

                <ColorList
                    colors={colors}
                    onDelete={deleteColor}
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
                    title="Add Color"
                    open={isModalVisible}
                    onOk={handleOk}
                    onCancel={handleCancel}
                    footer={null}
                >
                    <AddColorForm
                        onSuccess={() => {
                            handleOk();
                            getAllColors().then(setColors);
                        }}
                    />
                </Modal>
            </Content>
        </Container>
    );
};

export default Color;
