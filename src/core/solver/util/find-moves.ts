import { CardState } from "../../../types";

export const findMoves = (
  state: CardState
): CardState[] => {
  if (state.moves === 0) {
    return [];
  }
  const moves: CardState[] = [];
  const original = [...state.states];
  for (let i = 0; i < 16; i++) {
    if (!original[i]) {
      const copy = [...original];
      copy[i] = true;
    }
  }
  return moves;
};
