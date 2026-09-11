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

export interface AiAssistantResponse { answer: string; recommendations: AiProductReference[]; sources: AiProductReference[]; provider: string; }
export interface AiProductReference { productId: number; name: string; categoryName?: string; price: number; imageUrl?: string; stockQuantity: number; source: string; }
export interface AiEnrichmentResponse { suggestedTitle: string; suggestedDescription: string; suggestedCategory: string; suggestedTags: string[]; suggestedAltText: string; notice: string; provider: string; }
