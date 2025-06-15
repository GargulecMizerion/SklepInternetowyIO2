import React, { useState } from 'react';
import { Form, Input, Button, message } from 'antd';
import { addImage } from './ImageService';
import { FormWrapper } from '../shared/styles';

const AddImageForm = ({ onSuccess }) => {
    const [loading, setLoading] = useState(false);
    const [form] = Form.useForm();

    const onFinish = async (values) => {
        setLoading(true);
        try {
            await addImage(values);
            message.success('Image added successfully');
            form.resetFields();
            onSuccess();
        } catch (error) {
            message.error('Failed to add image');
        } finally {
            setLoading(false);
        }
    };

    return (
        <FormWrapper>
            <Form
                form={form}
                name="addImage"
                onFinish={onFinish}
                labelCol={{ span: 6 }}  
                wrapperCol={{ span: 16 }}
                autoComplete="off"
            >
                <Form.Item label="Color ID" name="color_id" rules={[{ required: true }]}>
                    <Input type="number" />
                </Form.Item>
                <Form.Item label="Product ID" name="product_id" rules={[{ required: true }]}>
                    <Input type="number" />
                </Form.Item>
                <Form.Item wrapperCol={{ offset: 6, span: 16 }}>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Add Image
                    </Button>
                </Form.Item>
            </Form>
        </FormWrapper>
    );
};

export default AddImageForm;
