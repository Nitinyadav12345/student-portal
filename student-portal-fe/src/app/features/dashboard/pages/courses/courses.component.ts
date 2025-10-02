import { Component } from '@angular/core';

@Component({
  selector: 'app-courses',
  template: `<div class="section"><h3>Courses</h3><p>List of enrolled courses.</p></div>`,
  styles: [`.section{padding:1rem;color:var(--color-text)} h3{margin:0 0 .5rem}`]
})
export class CoursesComponent {}
