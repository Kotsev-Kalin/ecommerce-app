import os
from typing import Any

import httpx
from langchain_core.tools import tool

BACKEND_URL = os.getenv("BACKEND_URL", "http://localhost:8080/api")


def _headers(user_token: str | None) -> dict[str, str]:
    return {"Authorization": f"Bearer {user_token}"} if user_token else {}


@tool
def search_products(query: str, limit: int = 3) -> list[dict[str, Any]]:
    """Find active catalog products for a customer query. This tool is read-only."""
    response = httpx.get(
        f"{BACKEND_URL}/products",
        params={"search": query},
        timeout=5.0,
    )
    response.raise_for_status()
    products = response.json()
    return products[:max(1, min(limit, 5))]


@tool
def get_product(product_id: int) -> dict[str, Any]:
    """Retrieve one authoritative catalog product by ID. This tool is read-only."""
    response = httpx.get(f"{BACKEND_URL}/products/{product_id}", timeout=5.0)
    response.raise_for_status()
    return response.json()


@tool
def get_my_orders(user_token: str) -> list[dict[str, Any]]:
    """Retrieve the current authenticated customer's orders. This tool is read-only."""
    response = httpx.get(
        f"{BACKEND_URL}/orders",
        headers=_headers(user_token),
        timeout=5.0,
    )
    response.raise_for_status()
    return response.json()
