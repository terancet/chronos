import {createStyles} from '@material-ui/core';

const styles = createStyles({
  navBar: {
    marginBottom: '32px',
    padding: '8px 0',
    background: '#fff',
    boxShadow: '0 2px 40px 1px rgba(221, 229, 252, 0.5)'
  },
  navTitle: {
    margin: '0 auto',
    paddingTop: '5px',
    fontSize: '20px '
  },
  navTitleName: {
    fontWeight: 'bold'
  },

  navButton: {
    width: '40px',
    height: '36px',
    padding: '0',
    backgroundColor: '#d6d6e3',
    borderRadius: '6px',
    marginRight: 40
  },
  navSettings: {},
  active: {
    backgroundColor: '#25cdda'
  },
  iconCalendar: {
    color: '#fff',
    fontSize: '22px'
  },
  iconSettings: {
    color: '#fff',
    fontSize: '20px'
  }
});

export default styles;
