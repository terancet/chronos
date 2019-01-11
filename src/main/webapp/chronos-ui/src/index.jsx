import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import {PersistGate} from 'redux-persist/integration/react';
import {MuiThemeProvider, createMuiTheme} from '@material-ui/core/styles';

import ErrorBoundary from 'modules/common/ErrorBoundary/ErrorBoundary';

import './index.css';
import Pages from './pages';
import * as serviceWorker from './serviceWorker';
import configureStore from './configurations/store';

const {store, persistor, history} = configureStore();

const theme = createMuiTheme({
  typography: {
    useNextVariants: true,
    fontSize: 16,
    color: '#4a4a4a'
  },
  palette: {
    primary: {
      main: '#25cdda'
    },
    secondary: {
      main: '#0044ff'
    },
    default: {
      main: '#ffffff'
    }
  },
  overrides: {
    MuiButton: {
      text: {
        color: 'white'
      }
    }
  }
});

ReactDOM.render(
  <Provider store={store}>
    <PersistGate loading={null} persistor={persistor}>
      <MuiThemeProvider theme={theme}>
        <ErrorBoundary>
          <Pages history={history} />
        </ErrorBoundary>
      </MuiThemeProvider>
    </PersistGate>
  </Provider>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
