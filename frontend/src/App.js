import './App.css'
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import ListProduct from "./components/ListProduct"
import ProductAdmin from "./components/ProductAdmin";

//Author: Sara

function App() {
    return (
        <BrowserRouter basename="/admin">
            <Routes>
                <Route path="/" element={<ListProduct />} />
                <Route path="/create-product" element={<ProductAdmin />} />
                <Route path="/update-product/:id" element={<ProductAdmin />} />
            </Routes>
        </BrowserRouter>
    );
}


export default App