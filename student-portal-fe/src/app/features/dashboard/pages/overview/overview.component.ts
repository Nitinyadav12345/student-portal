import { Component } from '@angular/core';

@Component({
  selector: 'app-overview',
  template: `<div class="section"><h3>Overview</h3><p>Summary cards and quick stats go here.</p></div>`,
  styles: [`.section{padding:1rem;color:var(--color-text)} h3{margin:0 0 .5rem}`]
})
export class OverviewComponent {}
