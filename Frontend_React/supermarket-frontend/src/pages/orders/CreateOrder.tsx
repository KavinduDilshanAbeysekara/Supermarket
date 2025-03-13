import { useEffect, useState } from "react";
import ProductType from "../../types/ProductType";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import jsPDF from "jspdf";
import Header from "../../components/Header";
import Footer from "../../components/Footer";

type Order = {
  product: ProductType;
  amount: number;
}

function CreateOrder() {
  const [products, setProducts] = useState<ProductType[]>([]);
  const { isAuthenticated, jwtToken } = useAuth();

  const [selectedProductId, setSelectedProductId] = useState<number>()
  const [orderAmount, setOrderAmount] = useState<string>()

  const config = {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  };

  useEffect(function () {
    async function loadProducts() {
      try {
        if (isAuthenticated) {
          const response = await axios.get("http://localhost:8081/products", config);
          setProducts(response.data);
        }
      } catch (error) {
        console.log(error);
      }
    }
    loadProducts();
  }, []);

  const [orderedProducts, setOrderedProducts] = useState<Order[]>([]);
  const [total, setTotal] = useState<number>(0);

  function addProductToOrder() {
    let selectedProduct;

    for (let index = 0; index < products.length; index++) {
      const element = products[index];
      if (element.id === Number(selectedProductId)) {
        selectedProduct = element;
      }

    }

    if (selectedProduct) {

      let newOrder = {
        product: selectedProduct,
        amount: Number(orderAmount)
      }

      const updatedOrder = [...orderedProducts, newOrder];
      setOrderedProducts(updatedOrder);
    }
  }

  function handleRemoveOrderItem (index: number) {
    setOrderedProducts((prevOrders) => prevOrders.filter((_, i) => i !== index));
  }

  // Calculate total when orderedProducts change
  useEffect(function () {
    let newTotal = 0;
    orderedProducts.forEach(function (order) {
      newTotal += order.amount * order.product.price;
    });
    setTotal(newTotal);
  }, [orderedProducts]);

  const navigate = useNavigate();

  async function saveOrder() {

    console.log(orderedProducts);
    // const productIds = orderedProducts.map((product) => product.id);

    // try {
    //   await axios.post(
    //     "http://localhost:8081/orders",
    //     {
    //       productIds: productIds,
    //     },
    //     config
    //   );

    //   navigate("/order"); // Navigate to orders page
    // } catch (error) {
    //   console.log(error);
    // }
  }

  // Generate PDF for the bill
  function generatePDF() {
    const doc = new jsPDF();

    doc.setFontSize(16);
    doc.text("Order Bill", 10, 10);

    // Table Header
    doc.setFontSize(12);
    doc.text("ID", 10, 20);
    doc.text("Description", 50, 20);
    doc.text("Item Price", 90, 20);
    doc.text("Quantity", 120, 20)
    doc.text("Price", 180, 20);

    // Table Content
    let yPosition = 30;
    orderedProducts.forEach((order, index) => {
      doc.text(index.toString(), 10, yPosition);
      doc.text(order.product.name, 50, yPosition);
      doc.text(`Rs. ${order.product.price}`, 90, yPosition);
      doc.text(order.amount.toString(), 120, yPosition);
      doc.text(`Rs. ${order.product.price * order.amount}`, 180, yPosition);
      yPosition += 10;
    });

    // Total
    doc.text("Total:", 10, yPosition);
    doc.text(`Rs. ${total}`, 180, yPosition, { align: "right" });

    // Save the PDF
    doc.save("order_bill.pdf");
  }

  return (
    <div className="min-h-screen flex flex-col">
      {/* Header */}
      <Header />

      {/* Main Content */}
      <main className="flex-grow flex justify-center bg-gray-50 py-5">
        <div className="bg-white rounded-lg shadow-lg p-6 w-full md:w-[90%] lg:w-[70%]">
          <div className="flex mb-8">
            {/* Product List Section */}
            <div className="w-[400px] border-r border-slate-200 pr-5">
              <h2 className="text-2xl font-semibold text-slate-800 mb-4">
                Add Order
              </h2>

              <div>
                <label className="block text-gray-800 mb-1">Product</label>
                <select
                  className="w-full border rounded-md p-2"
                  value={selectedProductId}
                  onChange={(e: any) => setSelectedProductId(e.target.value)}
                  required
                >
                  <option value="">Please select category</option>
                  {products.map((product) => (
                    <option key={product.id} value={product.id}>
                      {product.name}
                    </option>
                  ))}
                </select>
              </div>

              <div className="mt-3 w-full">
                <input className="w-full p-3 rounded-md h-8" placeholder="Enter Order Quantity" value={orderAmount} onChange={(e: any) => setOrderAmount(e.target.value)} />
              </div>

              <button
                type="button"
                className="mt-3 py-2 px-6 bg-blue-600 text-white rounded-lg hover:bg-green-700"
                onClick={addProductToOrder}
              >
                Add
              </button>
            </div>

            {/* Order Summary Section */}
            <div className="flex-1 pl-5">
              <h2 className="text-2xl font-semibold text-slate-800 mb-4">Order Summary</h2>
              <table className="w-full table-auto border-collapse text-left">
                <thead className="bg-gray-200">
                  <tr>
                    <th className="px-4 py-2 text-slate-800">ID</th>
                    <th className="px-4 py-2 text-slate-800">Description</th>
                    <th className="px-4 py-2 text-slate-800 text-nowrap">Item Price</th>
                    <th className="px-4 py-2 text-slate-800">Quantity</th>
                    <th className="px-4 py-2 text-slate-800 text-right">Price</th>
                    <th className="px-4 py-2 text-slate-800">Action</th>
                  </tr>
                </thead>
                <tbody>
                  {orderedProducts.map(function (order, index) {
                    return (
                      <tr key={index} className="border-t text-gray-950 border-slate-200 hover:bg-gray-50">
                        <td className="px-4 py-2">{index}</td>
                        <td className="px-4 py-2">{order.product.name}</td>
                        <td className="px-4 py-2 text-nowrap">Rs. {order.product.price}</td>
                        <td className="px-4 py-2">{order.amount}</td>
                        <td className="px-4 py-2 text-right text-nowrap">Rs. {order.product.price * order.amount}</td>
                        <td className="px-4 py-2 text-red-500 cursor-pointer text-center" onClick={() => handleRemoveOrderItem(index)}>x</td>
                      </tr>
                    );
                  })}
                  <tr>
                    <td colSpan={4} className="px-4 py-2 font-semibold text-slate-800">
                      <strong>Total</strong>
                    </td>
                    <td className="px-4 py-2 text-right font-semibold text-slate-800 text-nowrap">
                      <strong>Rs. {total}</strong>
                    </td>
                  </tr>
                </tbody>
              </table>

              {/* Buttons */}
              <div className="mt-6 text-center space-x-4">
                <button
                  type="button"
                  className="py-3 px-6 bg-slate-800 text-white rounded-lg hover:bg-slate-950"
                  onClick={saveOrder}
                >
                  Save Order
                </button>
                <button
                  type="button"
                  className="py-3 px-6 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                  onClick={generatePDF}
                >
                  Print Bill
                </button>
              </div>
            </div>
          </div>
        </div>
      </main>

      {/* Footer */}
      <Footer />
    </div>
  );
}

export default CreateOrder;
