import React, {useEffect, useState} from 'react'
import axios from "axios";
import { useNavigate } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';

//Author: Sara

export default function ListProduct() {

    const [products, setProducts] = useState([]);

    const navigator = useNavigate()

    const [errors, setErrors] = useState({});

    useEffect(() => {
        getAllProducts();
    }, [])

    function getAllProducts() {
        axios.get("/api/product")
            .then(response => setProducts(response.data))
            .catch(error => console.error(error));
    }

    function createNewProduct() {
        navigator('/create-product')
    }

    function updateProduct(id) {
        navigator(`/update-product/${id}`)
    }

    function removeProduct(id, productName) {
        const confirmDelete = window.confirm( `Are you sure you want to delete "${productName}"? This action cannot be undone.`
        );

        if (!confirmDelete) {
            return;
        }

        console.log(id);
        axios.delete(`/api/product/${id}`)
            .then(() => {
                alert(`"${productName}" was deleted successfully!`);
                getAllProducts();
            }).catch(e => {
            if (e.response && e.response.status === 409) {
                alert(e.response.data || "Cannot delete — product linked to pending order.");
            } else {
                console.error(e);
                alert("Something went wrong while deleting the product.");
            }
        })
    }

    return (
        <div className="container py-4" style={{ fontFamily: "Arial" }}>
            <h2 className='text-center mb-4'>Admin Product List</h2>
            <div className="text-center mb-4">
            <button
                type="button"
                className="btn btn-dark px-3 py-1 rounded-2"
                onClick={createNewProduct}>Create Product</button>
            </div>
            <div className="table-responsive">
            <table className="table table-bordered text-center align-left">
                <thead className="table-light">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Description</th>
                    <th>Image</th>
                    <th>Stock</th>
                    <th>Unit Price</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>

                {products.map(product => (
                    <tr key={product.id}>
                        <td>{product.id}</td>
                        <td>{product.name}</td>
                        <td>{product.category?.category}</td>
                        <td>{product.description}</td>
                        <td>
                            <img
                                src={product.imageUrl}
                                alt={product.name}
                                style={{ width: "80px", height: "100px", objectFit: "cover" }}
                                />
                        </td>
                        <td>{product.stockQuantity}</td>
                        <td>{product.unitPrice}</td>
                        <td className="d-flex justify-content-center gap-2">
                            <button className='btn btn-dark px-3 py-1 rounded-2' onClick={() => updateProduct(product.id)}>Update</button>
                            <button className='btn btn-dark px-3 py-1 rounded-2' onClick={() => removeProduct(product.id, product.name)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
        </div>
    );
}

