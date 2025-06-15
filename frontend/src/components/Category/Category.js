import React, { useState, useEffect } from 'react';
import { Layout, Modal, message, Input, Spin, Alert, Popconfirm } from 'antd';
import CategoryList from './CategoryList';
import AddCategoryForm from './AddCategoryForm';
import {
    AddButton,
    Container,
    SearchButton,
    DetailsCard,
    DetailItem,
    DeleteButton
} from '../shared/styles';
import { getAllCategories, getCategoryById, deleteCategoryById } from './CategoryService';

const { Content } = Layout;

const Category = () => {
    const [categories, setCategories] = useState([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [categoryId, setCategoryId] = useState('');
    const [searchedCategory, setSearchedCategory] = useState(null);
    const [loading, setLoading] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [hasSearched, setHasSearched] = useState(false);

    const deleteCategory = async (id) => {
            try {
                await deleteCategoryById(id);
                getAllCategories().then(setCategories);
                if (searchedCategory) {
                    setSearchedCategory(null);
                }
                setSuccessMessage('Category deleted successfully');
                message.success('Category deleted successfully');
            } catch (error) {
                message.error('Failed to delete category');
            }
        };

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const data = await getAllCategories();
                setCategories(data);
            } catch (error) {
                message.error('Failed to fetch categories');
            }
        };
        fetchCategories();
    }, []);

    const showModal = () => setIsModalVisible(true);
    const handleOk = () => {
        setIsModalVisible(false);
        setSuccessMessage('Category added successfully');
    };
    const handleCancel = () => setIsModalVisible(false);

    const handleSearch = async () => {
        if (!categoryId) {
            message.error('Please enter a category ID');
            return;
        }

        setLoading(true);
        setHasSearched(true);
        try {
            const data = await getCategoryById(categoryId);
            setSearchedCategory(data);
        } catch (error) {
            setSearchedCategory(null);
            message.error('Category not found');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container>
            <Content style={{ padding: '20px' }}>
                <AddButton type="primary" onClick={showModal}>
                    Add Category
                </AddButton>

                <div style={{ marginBottom: '20px' }}>
                    <Input
                        placeholder="Enter Category ID"
                        value={categoryId}
                        onChange={(e) => setCategoryId(e.target.value)}
                        style={{ width: '200px', marginBottom: '10px' }}
                    />
                    <SearchButton
                        type="primary"
                        onClick={handleSearch}
                        loading={loading}
                    >
                        Search Category
                    </SearchButton>
                </div>

                {loading ? (
                    <Spin size="large" style={{ display: 'block', marginTop: '20px' }} />
                ) : hasSearched && searchedCategory ? (
                    <DetailsCard style={{ position: 'relative' }}>
                        <h3 style={{ color: '#f326be', marginBottom: '16px' }}>Category Details</h3>
                        <DetailItem><span>ID:</span> {searchedCategory.id}</DetailItem>
                        <DetailItem><span>Name:</span> {searchedCategory.name}</DetailItem>
                        <DetailItem>
                            <span>Parent Category:</span> {searchedCategory.parentCategory ? searchedCategory.parentCategory.name : 'None'}
                        </DetailItem>
                        <Popconfirm
                            title="Are you sure you want to delete this category?"
                            onConfirm={() => deleteCategory(searchedCategory.id)}
                            okText="Yes"
                            cancelText="No"
                        >
                            <DeleteButton style={{ position: 'absolute', top: 31, right: 21 }}>
                                Delete
                            </DeleteButton>
                        </Popconfirm>
                    </DetailsCard>
                ) : hasSearched && !searchedCategory ? (
                    <Alert
                        message="Category not found"
                        type="warning"
                        showIcon
                        style={{ margin: '20px 0' }}
                    />
                ) : null}

                <CategoryList
                    categories={categories}
                    onDelete={deleteCategory}
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
                    title="Add Category"
                    open={isModalVisible}
                    onOk={handleOk}
                    onCancel={handleCancel}
                    footer={null}
                >
                    <AddCategoryForm
                        onSuccess={() => {
                            handleOk();
                            getAllCategories().then(setCategories);
                        }}
                    />
                </Modal>
            </Content>
        </Container>
    );
};

export default Category;
