import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChallengeTasks } from './challenge-tasks';

describe('ChallengeTasks', () => {
  let component: ChallengeTasks;
  let fixture: ComponentFixture<ChallengeTasks>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChallengeTasks]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChallengeTasks);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
