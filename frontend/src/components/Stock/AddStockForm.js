import React, { useState } from 'react';
import { Form, Input, Button, message } from 'antd';
import { addStock } from './StockService';
import { FormWrapper } from '../shared/styles';

const AddStockForm = ({ onSuccess }) => {
    const [loading, setLoading] = useState(false);
    const [form] = Form.useForm();

    const onFinish = async (values) => {
        setLoading(true);
        try {
            await addStock(values);
            message.success('Stock added successfully');
            form.resetFields();
            onSuccess();
        } catch (error) {
            message.error('Failed to add stock');
        } finally {
            setLoading(false);
        }
    };

    return (
        <FormWrapper>
            <Form
                form={form}
                name="addStock"
                onFinish={onFinish}
                labelCol={{ span: 6 }}
                wrapperCol={{ span: 16 }}
                autoComplete="off"
            >
                <Form.Item label="Product ID" name="productId" rules={[{ required: true }]}>
                    <Input type="number" />
                </Form.Item>
                <Form.Item label="Size ID" name="sizeId" rules={[{ required: true }]}>
                    <Input type="number" />
                </Form.Item>
                <Form.Item label="Color ID" name="colorId" rules={[{ required: true }]}>
                    <Input type="number" />
                </Form.Item>
                <Form.Item label="Quantity" name="quantity" rules={[{ required: true }]}>
                    <Input type="number" />
                </Form.Item>
                <Form.Item label="Price" name="price" rules={[{ required: true }]}>
                    <Input type="number" />
                </Form.Item>
                <Form.Item wrapperCol={{ offset: 6, span: 16 }}>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Add Stock
                    </Button>
                </Form.Item>
            </Form>
        </FormWrapper>
    );
};

export default AddStockForm;
