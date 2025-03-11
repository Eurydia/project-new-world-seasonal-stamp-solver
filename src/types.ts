export type CardState = {
  history: CardStateHistory[];
};

export type CardStateHistory = {
  stateId: number;
  lastPos: number;
  moves: number;
};

export type Result<T, K> =
  | {
      ok: true;
      data: T;
    }
  | {
      ok: false;
      other: K;
    };
