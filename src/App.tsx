import {
  Button,
  Container,
  CssBaseline,
  Grid2,
  Typography,
} from "@mui/material";
import { Fragment, useEffect, useState } from "react";

const isRowComplete = (pos: number, states: boolean[]) => {
  const row = Math.floor(pos / 4) * 4;

  for (let i = row; i < row + 4; i++) {
    if (!states[i]) {
      return false;
    }
  }
  return true;
};

const isColComplete = (pos: number, states: boolean[]) => {
  const col = pos % 4;
  for (let i = col; i < 13 + col; i += 4) {
    if (!states[i]) {
      return false;
    }
  }
  return true;
};

const isDiagFiveComplete = (
  pos: number,
  states: boolean[]
) => {
  if (pos % 5 !== 0) {
    return false;
  }

  for (let i = 0; i < 16; i += 5) {
    if (!states[i]) {
      return false;
    }
  }

  return true;
};

const isDiagThreeComplete = (
  pos: number,
  states: boolean[]
) => {
  if (pos % 3 !== 0) {
    return false;
  }

  for (let i = 3; i < 13; i += 3) {
    if (!states[i]) {
      return false;
    }
  }

  return true;
};

const randIntCollect = (count: number) => {
  const nums = new Set<number>();

  if (count <= 0 || count > 16) {
    return nums;
  }

  while (nums.size < count) {
    nums.add(Math.floor(Math.random() * 16));
  }
  return nums;
};

const restart = () => {
  const initStates = Array<boolean>(16);
  initStates.fill(false);
  const randNums = randIntCollect(6);
  return initStates.map((_, index) => randNums.has(index));
};

export const App = () => {
  const [states, setStates] = useState(restart);
  const [tries, setTries] = useState(3);
  const [lastChanged, setLastChanged] = useState<
    number | null
  >(null);

  useEffect(() => {
    if (lastChanged === null) {
      return;
    }
    setTries((prev) => {
      let next = prev;
      if (isColComplete(lastChanged, states)) {
        next++;
      }
      if (isRowComplete(lastChanged, states)) {
        next++;
      }
      if (isDiagFiveComplete(lastChanged, states)) {
        next++;
      }
      if (isDiagThreeComplete(lastChanged, states)) {
        next++;
      }
      return Math.min(next, 3);
    });
  }, [states, lastChanged]);

  return (
    <Fragment>
      <CssBaseline />
      <Container maxWidth="lg">
        <Typography>Tries: {tries}</Typography>
        <Grid2
          maxHeight="80vh"
          sx={{ aspectRatio: "1/1", padding: 4 }}
          container
          columns={4}
        >
          {states.map((state, index) => {
            return (
              <Grid2
                size={1}
                key={"k" + index.toString()}
                sx={{
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                  aspectRatio: "1/1",
                }}
              >
                <Button
                  disableRipple
                  disabled={state || tries === 0}
                  disableElevation
                  sx={{ height: "100%" }}
                  fullWidth
                  variant="contained"
                  color="success"
                  onClick={() => {
                    setStates((prev) => {
                      const next = [...prev];
                      next[index] = true;

                      return next;
                    });
                    setTries((prev) => prev - 1);
                    setLastChanged(index);
                  }}
                >
                  {/* {index} */}
                </Button>
              </Grid2>
            );
          })}
        </Grid2>
      </Container>
    </Fragment>
  );
};

