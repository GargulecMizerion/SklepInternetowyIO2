import React, { useState } from 'react';
import { Form, Input, Button, message } from 'antd';
import { addCategory } from './CategoryService';
import { FormWrapper } from '../shared/styles';

const AddCategoryForm = ({ onSuccess }) => {
    const [loading, setLoading] = useState(false);
    const [form] = Form.useForm();

    const onFinish = async (values) => {
        setLoading(true);
        try {
            await addCategory(values);
            message.success('Category added successfully');
            form.resetFields();
            onSuccess();
        } catch (error) {
            message.error('Failed to add category');
        } finally {
            setLoading(false);
        }
    };

    return (
        <FormWrapper>
            <Form
                form={form}
                name="addCategory"
                onFinish={onFinish}
                labelCol={{ span: 7 }}
                wrapperCol={{ span: 15 }}
                autoComplete="off"
            >
                <Form.Item label="Category Name" name="name" rules={[{ required: true }]}>
                    <Input />
                </Form.Item>
                <Form.Item label="Parent Category ID" name="parent_id">
                    <Input type="number" />
                </Form.Item>
                <Form.Item wrapperCol={{ offset: 6, span: 16 }}>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Add Category
                    </Button>
                </Form.Item>
            </Form>
        </FormWrapper>
    );
};

export default AddCategoryForm;
