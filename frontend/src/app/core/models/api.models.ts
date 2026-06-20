export interface Product {
  id?: number;
  name: string;
  description?: string;
  price: number;
  imageUrl?: string;
  stockQuantity: number;
  categoryId?: number;
  categoryName?: string;
  active?: boolean;
}

export interface CartItem {
  id: number;
  product: Product;
  quantity: number;
}

export interface OrderItem {
  id: number;
  product: Product;
  quantity: number;
  unitPrice: number;
  lineTotal: number;
}

export interface Order {
  id: number;
  totalAmount: number;
  status: string;
  shippingAddress: string;
  createdAt: string;
  items: OrderItem[];
}

export interface AuthResponse {
  token: string;
  tokenType: string;
  userId: number;
  email: string;
  fullName: string;
  roles: string[];
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface CheckoutRequest {
  shippingAddress: string;
}
