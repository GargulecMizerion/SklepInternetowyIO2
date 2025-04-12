import React, { useState } from 'react';
import { Form, Input, Button, message } from 'antd';
import { addProduct } from './ProductService';
import { FormWrapper } from './styles'; 

const AddProductForm = () => {
    const [loading, setLoading] = useState(false);

    const onFinish = async (values) => {
        setLoading(true);
        try {
            await addProduct(values);
            message.success('Product added successfully');
        } catch (error) {
            message.error('Failed to add product');
        } finally {
            setLoading(false);
        }
    };

    return (
        <FormWrapper>
            <Form
                name="addProduct"
                onFinish={onFinish}
                labelCol={{ span: 6 }}  
                wrapperCol={{ span: 16 }}
                autoComplete="off"
            >
                <Form.Item label="Product Name" name="name" rules={[{ required: true }]}>
                    <Input />
                </Form.Item>
                <Form.Item label="Description" name="description" rules={[{ required: true }]}>
                    <Input.TextArea />
                </Form.Item>
                <Form.Item label="Category ID" name="categoryId" rules={[{ required: true }]}>
                    <Input />
                </Form.Item>
                <Form.Item wrapperCol={{ offset: 6, span: 16 }}>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Add Product
                    </Button>
                </Form.Item>
            </Form>
        </FormWrapper>
    );
};

export default AddProductForm;
