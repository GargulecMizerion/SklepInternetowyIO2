import React, { useState } from 'react';
import { Form, Input, Button, message } from 'antd';
import { addSize } from './SizeService';
import { FormWrapper } from '../shared/styles';

const AddSizeForm = ({ onSuccess }) => {
    const [loading, setLoading] = useState(false);
    const [form] = Form.useForm();

    const onFinish = async (values) => {
        setLoading(true);
        try {
            await addSize(values);
            message.success('Size added successfully');
            form.resetFields();
            onSuccess();
        } catch (error) {
            message.error('Failed to add size');
        } finally {
            setLoading(false);
        }
    };

    return (
        <FormWrapper>
            <Form
                form={form}
                name="addSize"
                onFinish={onFinish}
                labelCol={{ span: 6 }}
                wrapperCol={{ span: 16 }}
                autoComplete="off"
            >
                <Form.Item label="Size" name="size_value" rules={[{ required: true }]}>
                    <Input />
                </Form.Item>
                <Form.Item label="Category ID" name="category_id" rules={[{ required: true }]}>
                    <Input type="number" />
                </Form.Item>
                <Form.Item wrapperCol={{ offset: 6, span: 16 }}>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Add Size
                    </Button>
                </Form.Item>
            </Form>
        </FormWrapper>
    );
};

export default AddSizeForm;
