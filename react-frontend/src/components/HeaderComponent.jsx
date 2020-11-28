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
import { Link, useHistory } from 'react-router-dom';
import FunctionsIcon from '@material-ui/icons/Functions';
import PictureAsPdfIcon from '@material-ui/icons/PictureAsPdf';
import GitHubIcon from '@material-ui/icons/GitHub';
import SaveIcon from '@material-ui/icons/Save';
import axios from 'axios';

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
  const preventDefault = (event) => event.preventDefault();
    let history = useHistory();
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const goToCatalog = () => {
   history.push('/catalog');
  };

  const goToOrders = () => {
      history.push("/orders");
  };
  const goToProducts = () => {
    history.push('/products');
  };
  const goToEmployee = () => {
    history.push('/employees');
  };
  const goToCustomers = () => {
    history.push('/customers');
    };
    const goToOrder = () => {
		
    }
  const showPdfReport = (title) => {
	switch (title) {
		case "Customers":
			window.open(props.PDF, '_blank');
			break;
	
		default:
			break;
	}
  }
  const savePdfReport = (title) => {
	switch (title) {
		case "Customers":
			axios.get("http://localhost:8080/api/v1/customer/report");
			break;
	
		default:
			break;
	}
  }
  const showGitPage = () => {
	window.open("https://github.com/Terross/OOPJavaSpringReactJS");
  }
  const findPi = () => {
	  window.open("http://localhost:3000/Pi");
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
		  <Button
				variant="contained"
				color="primary"
				size="small"
				className={classes.button}
				startIcon={<SaveIcon/>}
				onClick={() => savePdfReport(props.title)}
				disableElevation
			>
				Save Pdf
			</Button>
			<Button
				variant="contained"
				color="primary"
				size="small"
				className={classes.button}
				startIcon={<PictureAsPdfIcon />}
				onClick={() => showPdfReport(props.title)}
				disableElevation
			>
				Show Pdf
			</Button>
			<Button
				variant="contained"
				color="primary"
				size="small"
				className={classes.button}
				startIcon={<FunctionsIcon/>}
				onClick={() => findPi()}
				disableElevation
			>
				Find Pi
			</Button>
			<Button
				variant="contained"
				color="primary"
				size="small"
				className={classes.button}
				startIcon={<GitHubIcon/>}
				onClick={() => showGitPage()}
				disableElevation
			>
				GitHub page
			</Button>
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