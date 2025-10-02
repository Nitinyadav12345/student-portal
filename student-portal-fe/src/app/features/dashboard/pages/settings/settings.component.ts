import { Component } from '@angular/core';

@Component({
  selector: 'app-settings',
  template: `<div class="section"><h3>Settings</h3><p>Application preferences.</p></div>`,
  styles: [`.section{padding:1rem;color:var(--color-text)} h3{margin:0 0 .5rem}`]
})
export class SettingsComponent {}
