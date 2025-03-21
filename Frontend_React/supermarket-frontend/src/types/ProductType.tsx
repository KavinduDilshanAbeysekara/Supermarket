import CategoryType from "./CategoryType";

interface ProductType {
    id: number,
    name: string,
    price: number,
    stock: number,
    description: string,
    category?: CategoryType
}

export default ProductType;