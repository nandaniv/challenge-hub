import { TestBed } from '@angular/core/testing';

import { UserChallenge } from './user-challenge';

describe('UserChallenge', () => {
  let service: UserChallenge;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserChallenge);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
