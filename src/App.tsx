import { ThemeProvider } from "@emotion/react";
import { CheckRounded } from "@mui/icons-material";
import {
  alpha,
  Button,
  Container,
  createTheme,
  CssBaseline,
  Grid2,
  Stack,
  TextField,
  Toolbar,
  Typography,
} from "@mui/material";
import axios from "axios";
import { useState } from "react";

const theme = createTheme({
  palette: { contrastThreshold: 3 },
});

export const App = () => {
  const [states, setStates] = useState(() => {
    const initStates: boolean[] = [];
    for (let i = 0; i < 16; i++) {
      initStates.push(false);
    }
    return initStates;
  });
  const [result, setResult] = useState<
    {
      stateId: number;
      lastPos: number;
      moves: number;
    }[]
  >([]);
  const [tries, setTries] = useState("3");

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
      setResult([]);
      return;
    }
    if (
      respond.data === undefined ||
      respond.data.history === undefined
    ) {
      setResult([]);
      return;
    }

    setResult(
      respond.data.history as {
        stateId: number;
        lastPos: number;
        moves: number;
      }[]
    );
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Container maxWidth="lg">
        <Stack spacing={2}>
          <TextField
            type="number"
            inputMode="numeric"
            slotProps={{
              htmlInput: {
                max: 3,
                min: 0,
              },
            }}
            value={tries}
            onChange={(e) => setTries(e.target.value)}
          />
          <Toolbar disableGutters>
            <Button
              disableElevation
              onClick={handleSubmit}
              variant="contained"
            >
              Submit
            </Button>
          </Toolbar>
          <Grid2
            maxHeight={500}
            maxWidth={500}
            sx={{ aspectRatio: "1/1" }}
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
                    disableElevation
                    sx={{
                      height: "100%",
                      backgroundColor: state
                        ? "primary.light"
                        : "primary.main",
                    }}
                    fullWidth
                    variant="contained"
                    onClick={() => {
                      setStates((prev) => {
                        const next = [...prev];
                        next[index] = !prev[index];
                        return next;
                      });
                    }}
                  >
                    {state && <CheckRounded />}
                  </Button>
                </Grid2>
              );
            })}
          </Grid2>
          {result.length > 0 && (
            <Grid2
              sx={{
                aspectRatio: "1/1",
                maxHeight: 500,
                maxWidth: 500,
                borderStyle: "solid",
                borderWidth: 8,
                borderColor: "primary.dark",
              }}
              container
              columns={4}
            >
              {Array(16)
                .fill(0)
                .map((_, index) => {
                  let order = "";
                  for (const [
                    itemIndex,
                    item,
                  ] of result.entries()) {
                    if (item.lastPos === index) {
                      order = (itemIndex + 1).toString();
                      break;
                    }
                  }
                  const bgAlpha =
                    order !== ""
                      ? (100 / result.length / 100) *
                        Number.parseInt(order)
                      : 0;
                  const bgColor = alpha(
                    theme.palette.primary.main,
                    bgAlpha
                  );
                  return (
                    <Grid2
                      size={1}
                      key={"k" + index.toString()}
                      sx={{
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        aspectRatio: "1/1",
                        backgroundColor: bgColor,
                      }}
                    >
                      <Typography
                        fontWeight={700}
                        variant="h5"
                      >
                        {order}
                      </Typography>
                    </Grid2>
                  );
                })}
            </Grid2>
          )}
        </Stack>
      </Container>
    </ThemeProvider>
  );
};

