describe('The login page', () => {
  beforeEach(() => {
    cy.login("fse1234", "fse1234");
  })

  it('passes', () => {
    cy.get('h5').should('contain', 'Search')
    cy.get('div').contains('Sign Out')
  })
})

describe('The login page - incorrect credentials', () => {
  beforeEach(() => {
    cy.login("fse12342", "fse1234");
  })

  it('passes', () => {
    cy.get('h5').should('contain', 'Login')
  })
})

describe('Search', () => {
  beforeEach(() => {
    cy.login("fse1234", "fse1234");
  })

  it('passes', () => {
    cy.get('h5').should('contain', 'Search')
    cy.get('div').contains('Sign Out')
    cy.url().should('include', '/home')
    cy.get('[type="radio"]').check('Menu')
    cy.get("input[name=search]").type("PIZZA");
    cy.get('[type="submit"]').click()  
    cy.get('div').should('contain', 'Name')
  })
})

describe('Login Sign Out', () => {
  beforeEach(() => {
    cy.login("fse1234", "fse1234");
  })

  it('passes', () => {
    cy.get('h5').should('contain', 'Search')
    cy.get('div').contains('Sign Out')
    cy.url().should('include', '/home')
    cy.get('[type="radio"]').check('Menu')
    cy.get("input[name=search]").type("PIZZA");
    cy.get('[type="submit"]').click()  
    cy.get('div').should('contain', 'Name')
    cy.get('a.sidenav-signout').click()
    cy.get('h5').should('contain', 'Login')
  })
})


