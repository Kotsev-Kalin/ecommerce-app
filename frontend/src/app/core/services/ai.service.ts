import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AiAssistantResponse, AiEnrichmentResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class AiService {
  constructor(private readonly http: HttpClient) {}
  askAssistant(question: string): Observable<AiAssistantResponse> { return this.http.post<AiAssistantResponse>(`${environment.apiUrl}/ai/assistant`, { question }); }
  suggestProductEnrichment(payload: { title: string; description?: string; category?: string; imageUrl?: string }): Observable<AiEnrichmentResponse> { return this.http.post<AiEnrichmentResponse>(`${environment.apiUrl}/ai/admin/product-enrichment`, payload); }
}
