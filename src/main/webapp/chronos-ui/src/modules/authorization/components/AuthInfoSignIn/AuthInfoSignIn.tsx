import * as React from 'react';
import {Button, withStyles, WithStyles} from '@material-ui/core';

import Logo from 'shared/assets/img/logo.svg';
import {history} from 'configurations/store';

import * as theme from './AuthInfoSignIn.scss';
import styles from './styles';

interface IProps extends WithStyles<typeof styles> {}

const AuthInfoSignIn: React.FunctionComponent<IProps> = ({classes}) => (
  <div className={theme.root}>
    <div className={theme.content}>
      <div className={theme.logo}>
        <img src={Logo} alt="logo" width="140" height="41" />
      </div>
      <h2 className={theme.header}>Do you already have an account?</h2>
      <p className={theme.info}>
        That’s awesome! You can login by clicking on the button below. To skip
        this next time, you can ask us to remember your login credentials.
      </p>
      <Button
        variant="outlined"
        color="primary"
        size="large"
        className={classes.button}
        onClick={() => history.push('/sign-in')}
      >
        Sign In
      </Button>
    </div>
  </div>
);

AuthInfoSignIn.defaultProps = {};

export default withStyles(styles)(AuthInfoSignIn);
