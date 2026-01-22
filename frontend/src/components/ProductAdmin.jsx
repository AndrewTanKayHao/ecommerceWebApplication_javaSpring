import React, {useEffect, useState} from 'react'
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.min.css';

//Author: Sara

const ProductAdmin = () => {

    const [name, setName] = useState('')
    const [description, setDescription] = useState('')
    const [stockQuantity, setStockQuantity] = useState(0)
    const [unitPrice, setUnitPrice] = useState(0.00)
    const [imageUrl, setImageUrl] = useState('');
    const [categoryId, setCategoryId] = useState('');
    const [categories, setCategories] = useState([]);

    const [errors, setErrors] = useState({});

    const navigator = useNavigate();

    const{id} = useParams();

    useEffect(() => {
        if(id) {
            axios.get(`/api/product/${id}`)
                .then((response) => {
                    setName(response.data.name);
                    setDescription(response.data.description);
                    setStockQuantity(response.data.stockQuantity);
                    setUnitPrice(response.data.unitPrice);
                    setImageUrl(response.data.imageUrl);
                    setCategoryId(response.data.category.id);
                }).catch(error => {
                console.error(error);
            })
        }
    }, [id]);

    useEffect(() => {
        axios.get('/api/category')
            .then((response) => {
                setCategories(response.data);
            })
            .catch((error) => console.error(error));
    }, []);

    function saveOrUpdateProduct(e){
        e.preventDefault();
        setErrors({});
        const product = {name, description, imageUrl, stockQuantity, unitPrice, category: { id: categoryId}};
        console.log(product);

        if(id) {
            axios.put(`/api/product/${id}`, product)
                .then(response => {
                    console.log(response.data);
                    alert("Product updated successfully!");
                    navigator('/')
                })
                .catch(e=> {
                    if (e.response && e.response.status === 400) {
                        setErrors(e.response.data);
                    } else {
                        console.error(e);
                        alert("Something went wrong!");
                    }
                });
        } else {
            axios.post('/api/product/create', product)
                .then((response) => {
                    console.log(response.data);
                    alert("Product created successfully!");
                    navigator('/');
                })
                .catch(e=> {
                    if (e.response && e.response.status === 400) {
                        setErrors(e.response.data);
                    } else {
                        console.error(e);
                        alert("Category must be selected!");
                    }
                });
        }
    }

    function pageTitle() {
        if(id){
            return <h2 className='text-center'>Update Product</h2>
        } else {
            return <h2 className='text-center'>Create Product</h2>
        }

    }

    return (
        <div className="container my-5">
            <div className="row justify-content-center">
                <div className="card border-0 border-bottom rounded-3" style={{ backgroundColor: "#fff", maxWidth: "1000px", margin: "0 auto" }}>
                    {
                        pageTitle()
                    }
                    <div className="card-body p-5" style={{ width: "100%", margin: "0 auto" }}>
                        <form>
                            <div className="mb-3">
                                <label className="form-label fw-medium text-dark">Name: </label>
                                <input
                                    type='text'
                                    placeholder='Enter name'
                                    name='name'
                                    value={name}
                                    className="form-control border-bottom rounded-0 px-3 py-2"
                                    onChange={(e) => setName(e.target.value)}
                                >
                                </input>
                                {errors.name && <div className="text-danger small mt-1">{errors.name}</div>}
                            </div>

                            <div className="mb-3">
                                <label className= "form-label fw-medium text-dark">Description: </label>
                                <input
                                    type='text'
                                    placeholder='Enter description'
                                    name='description'
                                    value={description}
                                    className="form-control border-bottom rounded-0 px-3 py-2"
                                    onChange={(e) => setDescription(e.target.value)}
                                >
                                </input>
                                {errors.description && <div className="text-danger small mt-1">{errors.description}</div>}
                            </div>
                            <div className="mb-3">
                                <label className="form-label fw-medium text-dark">Image URL:</label>
                                <input
                                    type='text'
                                    placeholder='Enter image path'
                                    name='imageUrl'
                                    value={imageUrl}
                                    className="form-control border-bottom rounded-0 px-3 py-2"
                                    style = {{backgroundColor: "#fdfdfd"}}
                                    onChange={(e) => setImageUrl(e.target.value)}
                                />
                                {errors.imageUrl && <div className="text-danger small mt-1">{errors.imageUrl}</div>}
                            </div>
                            <div className="mb-3">
                                <label className="form-label fw-medium text-dark">Category: </label>
                                <select
                                    name='category'
                                    className="form-control border-bottom rounded-0 px-3 py-2"
                                    value={categoryId}
                                    onChange={(e) => setCategoryId(e.target.value)}
                                >
                                    <option value=''>-- Select Category --</option>
                                    {categories.map((cat) => (
                                        <option key={cat.id} value={cat.id}>
                                            {cat.category}
                                        </option>
                                        ))}
                                </select>
                                {errors.category && <div className="text-danger small mt-1">{errors.category}</div>}
                            </div>

                            <div className="mb-3">
                                <label className="form-label fw-medium text-dark">Stock Quantity: </label>
                                <input
                                    type='number'
                                    placeholder='Enter stock quanity'
                                    name='stock'
                                    value={stockQuantity}
                                    className="form-control border-bottom rounded-0 px-3 py-2"
                                    style = {{backgroundColor: "#fdfdfd"}}
                                    onChange={(e) => setStockQuantity(parseInt(e.target.value))}
                                >
                                </input>
                                {errors.stockQuantity && <div className="text-danger small mt-1">{errors.stockQuantity}</div>}
                            </div>

                            <div className="mb-3">
                                <label className="form-label fw-medium text-dark">Unit Price: </label>
                                <input
                                    type='number'
                                    placeholder='Enter price'
                                    name='price'
                                    value={unitPrice}
                                    className="form-control border-bottom rounded-0 px-3 py-2"
                                    onChange={(e) => setUnitPrice(parseFloat(e.target.value))}
                                >
                                </input>
                                {errors.unitPrice && <div className="text-danger small mt-1">{errors.unitPrice}</div>}
                            </div>
                            <div className = "text-center mt-5">
                            <button
                                type="button"
                                className="btn btn-dark fx-medium px-5 py-2 rounded-2"
                                onClick={saveOrUpdateProduct}>Submit
                            </button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>

        </div>
    )
}
export default ProductAdmin;