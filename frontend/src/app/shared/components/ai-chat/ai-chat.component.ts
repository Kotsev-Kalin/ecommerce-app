import { Component } from '@angular/core';
import { AiAssistantResponse } from '../../../core/models/api.models';
import { AiService } from '../../../core/services/ai.service';
@Component({ selector: 'app-ai-chat', templateUrl: './ai-chat.component.html' })
export class AiChatComponent {
  question = ''; response?: AiAssistantResponse; loading = false; error = '';
  constructor(private readonly ai: AiService) {}
  ask(): void { if (!this.question.trim()) { this.error = 'Enter a shopping question.'; return; } this.loading = true; this.error = ''; this.ai.askAssistant(this.question).subscribe({ next: response => { this.response = response; this.loading = false; }, error: error => { this.error = error?.error?.message || 'The shopping assistant is unavailable.'; this.loading = false; } }); }
}
