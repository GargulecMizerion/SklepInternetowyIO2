import React, { useState } from 'react';
import { Form, Input, Button, message } from 'antd';
import { addColor } from './ColorService';
import { FormWrapper } from '../shared/styles';

const AddColorForm = ({ onSuccess }) => {
    const [loading, setLoading] = useState(false);
    const [form] = Form.useForm();

    const onFinish = async (values) => {
        setLoading(true);
        try {
            await addColor(values);
            message.success('Color added successfully');
            form.resetFields();
            onSuccess();
        } catch (error) {
            message.error('Failed to add color');
        } finally {
            setLoading(false);
        }
    };

    return (
        <FormWrapper>
            <Form
                form={form}
                name="addColor"
                onFinish={onFinish}
                labelCol={{ span: 6 }}
                wrapperCol={{ span: 16 }}
                autoComplete="off"
            >
                <Form.Item label="Color" name="color" rules={[{ required: true }]}>
                    <Input />
                </Form.Item>
                <Form.Item wrapperCol={{ offset: 6, span: 16 }}>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Add Color
                    </Button>
                </Form.Item>
            </Form>
        </FormWrapper>
    );
};

export default AddColorForm;
