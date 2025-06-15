import React, { useState, useEffect } from 'react';
import { Layout, Modal, message, Input, Spin, Alert, Popconfirm } from 'antd';
import ImageList from './ImageList';
import AddImageForm from './AddImageForm';
import {
    AddButton,
    Container,
    SearchButton,
    DetailsCard,
    DetailItem,
    DeleteButton
} from '../shared/styles';
import { getAllImages, getImageById, getImageByProductId, deleteImageById } from './ImageService';

const { Content } = Layout;

const Image = () => {
    const [images, setImages] = useState([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [imageId, setImageId] = useState('');
    const [productId, setProductId] = useState('');
    const [searchedImage, setSearchedImage] = useState(null);
    const [loading, setLoading] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [hasSearched, setHasSearched] = useState(false);

    const deleteImage = async (id) => {
        try {
            await deleteImageById(id);
            getAllImages().then(setImages);
            if (searchedImage) {
                setSearchedImage(prev => prev.filter(p => p.id !== id));
            }
            setSuccessMessage('Image deleted successfully');
            message.success('Image deleted successfully');
        } catch (error) {
            message.error('Failed to delete image');
        }
    };

    useEffect(() => {
        const fetchImages = async () => {
            try {
                const data = await getAllImages();
                setImages(data);
            } catch (error) {
                message.error('Failed to fetch images');
            }
        };
        fetchImages();
    }, []);

    const showModal = () => setIsModalVisible(true);
    const handleOk = () => {
        setIsModalVisible(false);
        setSuccessMessage('Image added successfully');
    };
    const handleCancel = () => setIsModalVisible(false);

    const handleSearch = async () => {
        if (!imageId) {
            message.error('Please enter a image ID');
            return;
        }

        setLoading(true);
        setHasSearched(true);
        try {
            const data = await getImageById(imageId);
            setSearchedImage([data]);
        } catch (error) {
            setSearchedImage(null);
            message.error('Image not found');
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
            const data = await getImageByProductId(productId);
            setSearchedImage(data);
        } catch (error) {
            setSearchedImage(null);
            message.error('Image not found for this product ID');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container>
            <Content style={{ padding: '20px' }}>
                <AddButton type="primary" onClick={showModal}>
                    Add Image
                </AddButton>

                <div style={{ marginBottom: '20px' }}>
                    <div style={{ marginBottom: '10px' }}>
                        <Input
                            placeholder="Enter Image ID"
                            value={imageId}
                            onChange={(e) => setImageId(e.target.value)}
                            style={{ width: '200px', marginBottom: '10px' }}
                        />
                        <SearchButton
                            type="primary"
                            onClick={handleSearch}
                            loading={loading}
                        >
                            Search by Image ID
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
                ) : hasSearched && Array.isArray(searchedImage) && searchedImage.length > 0 ? (
                    <>
                        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '30px', margin: '30px 0' }}>
                            {searchedImage.map((img) => (
                                <DetailsCard key={img.id} style={{ margin: '0px', position: 'relative' }}>
                                    <h3 style={{ color: '#f326be', marginBottom: '16px' }}>Image Details</h3>
                                    <DetailItem><span>ID:</span> {img.id}</DetailItem>
                                    <DetailItem>
                                        <span>Color:</span> {img.color ? img.color.color : 'No color'}
                                    </DetailItem>
                                    <DetailItem>
                                        <span>Product:</span> {img.product ? img.product.name : 'No product'}
                                    </DetailItem>
                                    <Popconfirm
                                        title="Are you sure you want to delete this image?"
                                        onConfirm={() => deleteImage(img.id)}
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
                        message="Image(s) not found"
                        type="warning"
                        showIcon
                        style={{ margin: '20px 0' }}
                    />
                ) : null}

                <ImageList
                    images={images}
                    onDelete={deleteImage}
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
                    title="Add Image"
                    open={isModalVisible}
                    onOk={handleOk}
                    onCancel={handleCancel}
                    footer={null}
                >
                    <AddImageForm
                        onSuccess={() => {
                            handleOk(); 
                            getAllImages().then(setImages);
                        }}
                    />
                </Modal>
            </Content>
        </Container>
    );
};

export default Image;
