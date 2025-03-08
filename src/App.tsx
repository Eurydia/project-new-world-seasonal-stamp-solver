import {
  Button,
  Container,
  CssBaseline,
  Grid2,
  Typography,
} from "@mui/material";
import axios from "axios";
import { Fragment, useState } from "react";

export const App = () => {
  const [states, setStates] = useState(() => {
    const initStates: boolean[] = [];
    for (let i = 0; i < 16; i++) {
      initStates.push(false);
    }
    return initStates;
  });
  const [result, setResult] = useState<string[]>([]);
  const [tries, setTries] = useState(3);

  const handleSubmit = async () => {
    const stateIdBinary = states
      .map((state) => (state ? "1" : "0"))
      .join("");
    const stateId = Number.parseInt(stateIdBinary, 2);
    console.debug(stateId);
    const respond = await axios(
      `http://localhost:8080/?state=${stateId}&moves=${tries}`,
      {
        proxy: false,
      }
    );
    if (respond.status !== 200) {
      return;
    }
    setResult(
      (respond.data as { history: number[] }).history.map(
        (past) => past.toString(2).padStart(16, "0")
      )
    );
  };

  return (
    <Fragment>
      <CssBaseline />
      <Container maxWidth="lg">
        <Button
          disableElevation
          onClick={handleSubmit}
          variant="contained"
        >
          Submit
        </Button>
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
                  }}
                >
                  {/* {index} */}
                </Button>
              </Grid2>
            );
          })}
        </Grid2>
        {result.map((r, index) => (
          <Grid2
            key={"step" + index}
            sx={{
              aspectRatio: "1/1",
              padding: 4,
              maxWidth: 200,
            }}
            container
            columns={4}
          >
            {r.split("").map((state, indexR) => {
              return (
                <Grid2
                  size={1}
                  key={"k" + indexR.toString()}
                  sx={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    aspectRatio: "1/1",
                    backgroundColor:
                      state === "1"
                        ? "primary.dark"
                        : "primary.light",
                  }}
                />
              );
            })}
          </Grid2>
        ))}
      </Container>
    </Fragment>
  );
};

