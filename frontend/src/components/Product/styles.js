import styled from 'styled-components';
import { Button } from 'antd';

export const FormWrapper = styled.div`
  .ant-form-item-label {
    font-weight: bold;
    font-size: 14px;
  }

  .ant-form-item {
    margin-bottom: 16px;
  }

  .ant-input, .ant-input-textarea {
    width: 100%;
    margin-bottom: 8px;
  }

  .ant-btn {
    background-color: #f326be; 
    border-color: #f326be;
    color: white;
    font-weight: bold;
    width: 100%; 
    &:hover {
      background-color: #c41196 !important; 
      border-color: #c41196 !important; 
    }
  }
`;

export const ProductContainer = styled.div`
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin: 20px 0;
`;

export const AddProductButton = styled(Button)`
  background-color: #f326be;
  color: white;
  border: none;
  &:hover {
    background-color: #c41196 !important; 
    border-color: #c41196 !important;  
  }
  margin-bottom: 20px;
`;

export const SearchProductButton = styled(Button)`
  background-color: #f326be;
  color: white;
  border: none;
  &:hover {
    background-color: #c41196 !important;
    border-color: #c41196 !important;  
  }
  margin-left: 10px; /* Dla lepszego odstępu od inputa */
`;

export const ModalContent = styled.div`
  padding: 20px;
`;

export const TableWrapper = styled.div`
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
`;

export const ProductDetailsCard = styled.div`
  max-width: 400px;
  background-color: #ffffff;
  border-radius: 12px;
  padding: 20px;
  margin: 30px 0; /* góra/dół, bez auto */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-left: 6px solid #f326be;
`;

export const ProductDetailItem = styled.p`
  font-size: 16px;
  margin-bottom: 12px;
  color: #333;

  span {
    font-weight: bold;
    margin-right: 8px;
    color: #111;
  }
`;

