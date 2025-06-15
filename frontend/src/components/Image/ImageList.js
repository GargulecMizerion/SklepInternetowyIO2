import React from 'react';
import { Table, Popconfirm } from 'antd';
import { TableWrapper, DeleteButton } from '../shared/styles';

const ImageList = ({ images, onDelete }) => {
    const handleDelete = async (id) => {
        await onDelete(id);
    };

    const columns = [
        { title: 'Image ID', dataIndex: 'id', key: 'id'},
        { title: 'Color', dataIndex: 'color', key: 'color', render: (color) => color ? color.color : 'N/A' },
        { title: 'Product', dataIndex: 'product', key: 'product', render: (product) => product ? product.name : 'N/A' },
        {
            title: 'Actions',
            key: 'actions',
            render: (_, record) => (
                <Popconfirm
                    title="Are you sure you want to delete this image?"
                    onConfirm={() => handleDelete(record.id)}
                    okText="Yes"
                    cancelText="No"
                >
                    <DeleteButton>Delete</DeleteButton>
                </Popconfirm>
            ),
        },
    ];    

    return (
        <TableWrapper>
            <Table dataSource={images} columns={columns} rowKey="id" />
        </TableWrapper>
    );
};

export default ImageList;
