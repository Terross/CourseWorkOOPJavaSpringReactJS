import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import { withStyles } from '@material-ui/core/styles';
import { useHistory } from 'react-router-dom';

const StyledMenu = withStyles({
    paper: {
      border: '1px solid #d3d4d5',
    },
  })((props) => (
    <Menu
      elevation={0}
      getContentAnchorEl={null}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'center',
      }}
      transformOrigin={{
        vertical: 'top',
        horizontal: 'center',
      }}
      {...props}
    />
  ));

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
}));

export default function ButtonAppBar(props) {
  const classes = useStyles();
  const [anchorEl, setAnchorEl] = React.useState(null);
    let history = useHistory();
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const goToCatalog = () => {
   
  };

  const goToOrders = () => {
      
  };
  const goToProducts = () => {

  };
  const goToEmployee = () => {
    history.push('/employees');
  };
  const goToCustomers = () => {
    history.push('/customers');
    };
    const goToOrder = () => {

    }
  
  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>

          <Button edge="start"
           className={classes.menuButton}
            color="inherit"
             aria-label="menu"
             onClick={handleClick}>
            <MenuIcon />
          </Button>

          <Typography variant="h6" className={classes.title}>
            {props.title}
          </Typography>

          <Button color="inherit">Login</Button>
        </Toolbar>
      </AppBar>
      <StyledMenu
        id="customized-menu"
        anchorEl={anchorEl}
        keepMounted
        open={Boolean(anchorEl)}
        onClose={handleClose}
      >
        <MenuItem onClick={goToCatalog }>Catalog</MenuItem>
        <MenuItem onClick={goToProducts}>Products</MenuItem>
        <MenuItem onClick={goToOrders}>Orders</MenuItem>
        <MenuItem onClick={goToEmployee}>Employee</MenuItem>
        <MenuItem onClick={goToCustomers}>Customers</MenuItem>
      </StyledMenu>
    </div>
  );
}